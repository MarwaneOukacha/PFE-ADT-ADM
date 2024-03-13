package ma.adria.document_validation.administration.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ma.adria.document_validation.administration.dto.Utilisateurdto;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.userProfile;
import ma.adria.document_validation.administration.model.enums.userStatus;
import ma.adria.document_validation.administration.reposetiry.UserReposetory;
import ma.adria.document_validation.administration.services.UserService;
import ma.adria.document_validation.administration.services.external.keycloakService;

@Service
public class UserServiceV01 implements UserService {
	@Autowired
	private UserReposetory utilisateurRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${app.key_cloak.users}")
	private String keycloakUrlUser;
	@Value("${app.key_cloak.client_secret}")
	private String client_secret;
	@Value("${app.key_cloak.auth}")
	private String keycloakUrlToken;
	@Autowired
	private keycloakService kycservice;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Utilisateurdto> createUtilisateur(Utilisateur utilisateur) {
		// Ajouter l'utilisateur à keyckloak
		String token = kycservice.extractTokenFromResponse(
				kycservice.GetTokenFromKeycloakWithClient_credentials(client_secret, keycloakUrlToken));
		System.out.println("token==" + token);
		kycservice.addUserToKeycloak(utilisateur.getEmail(), utilisateur.getEmail(), utilisateur.getPassword(), token,
				keycloakUrlUser, utilisateur.getPrenom(), utilisateur.getNom());
		// Ajouter l'utilisateur à la DB
		utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
		utilisateur.setProfil(userProfile.user.toString());
		utilisateur.setStatut(userStatus.desactiver.toString());
		Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
		// il faut ajouter la partie de verification par email
		// céer Utilisateurdto
		Utilisateurdto dto = new Utilisateurdto();
		BeanUtils.copyProperties(savedUtilisateur, dto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateurs() {
		// il faut modifier le retour de la methode
		List<Utilisateurdto> utilisateurs = utilisateurRepository.findAll().stream().map(this::convertToUtilisateurdto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Utilisateurdto> getUtilisateurById(long id) {

		Utilisateur utilisateur = utilisateurRepository.findById(id).get();
		Utilisateurdto dto = new Utilisateurdto();
		BeanUtils.copyProperties(utilisateur, dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Utilisateurdto> updateUtilisateur(String email, Utilisateur utilisateur) {
		Utilisateur user = utilisateurRepository.findByEmail(email);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (utilisateur.getNom() != null && !utilisateur.getNom().equals("")) {
				user.setNom(utilisateur.getNom());
			}
			if (utilisateur.getPrenom() != null && !utilisateur.getPrenom().equals("")) {
				user.setPrenom(utilisateur.getPrenom());
			}
			if (utilisateur.getPassword() != null && !utilisateur.getPassword().equals("")) {

				user.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
			}
			if (utilisateur.getNum_tele() != null && !utilisateur.getNum_tele().equals("")) {

				user.setNum_tele(utilisateur.getNum_tele());
			}

		}
		utilisateurRepository.save(user);
		Utilisateurdto updatedUtilisateur = new Utilisateurdto();
		BeanUtils.copyProperties(user, updatedUtilisateur);
		return new ResponseEntity<>(updatedUtilisateur, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> bloquerUtilisateur(String email) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurRepository.findByEmail(email);
		user.setStatut("bloquer");
		utilisateurRepository.save(user);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<HttpStatus> activerUtilisateur(String email) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurRepository.findByEmail(email);
		user.setStatut(userStatus.activer.toString());
		utilisateurRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> desactiverUtilisateur(String email) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurRepository.findByEmail(email);
		user.setStatut(userStatus.desactiver.toString());
		utilisateurRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> updateNbrTransactionUtilisateur(String email, int nbr) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurRepository.findByEmail(email);
		user.setNbr_max_transactions(nbr);
		utilisateurRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> debloquerUtilisateur(String email) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurRepository.findByEmail(email);
		user.setStatut(userStatus.debloquer.toString());
		utilisateurRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByPrenom(String Prenom) {
		// TODO Auto-generated method stub
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByPrenom(Prenom).stream()
				.map(this::convertToUtilisateurdto).collect(Collectors.toList());

		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByNom(String nom) {
		// TODO Auto-generated method stub
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByNom(nom).stream()
				.map(this::convertToUtilisateurdto).collect(Collectors.toList());

		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursBloque() {
		// TODO Auto-generated method stub
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByStatut(userStatus.bloquer.toString())
				.stream().map(this::convertToUtilisateurdto).collect(Collectors.toList());
		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDeBloque() {
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByStatut(userStatus.debloquer.toString())
				.stream().map(this::convertToUtilisateurdto).collect(Collectors.toList());
		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursActiver() {
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByStatut(userStatus.activer.toString())
				.stream().map(this::convertToUtilisateurdto).collect(Collectors.toList());
		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDesactiver() {
		List<Utilisateurdto> listutilisqteurs = utilisateurRepository.findByStatut(userStatus.desactiver.toString())
				.stream().map(this::convertToUtilisateurdto).collect(Collectors.toList());
		return new ResponseEntity<>(listutilisqteurs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Utilisateurdto> getUtilisateurByEmail(String email) {
		return new ResponseEntity<>(convertToUtilisateurdto(utilisateurRepository.findByEmail(email)), HttpStatus.OK);
	}

	private Utilisateurdto convertToUtilisateurdto(Utilisateur user) {
		Utilisateurdto utilisateurdto = new Utilisateurdto();
		utilisateurdto.setEmail(user.getEmail());
		utilisateurdto.setNom(user.getNom());
		utilisateurdto.setPrenom(user.getPrenom());
		utilisateurdto.setNbr_max_transactions(user.getNbr_max_transactions());
		utilisateurdto.setNum_tele(user.getNum_tele());
		utilisateurdto.setSize_max(user.getSize_max());
		utilisateurdto.setStatut(user.getStatut());
		return utilisateurdto;
	}

	@Override
	public ResponseEntity<List<Utilisateurdto>> rechercherUtilisateurMulticriteres(Utilisateurdto dto) {
		List<Utilisateur> utilisateurs=utilisateurRepository.findAll();
		List<Utilisateur> utilisateursResult=utilisateurs.stream()
                .filter(utilisateur -> estFiltreNom(utilisateur, dto))
                .filter(utilisateur -> estFiltrePrenom(utilisateur, dto))
                .filter(utilisateur -> estFiltreNumTele(utilisateur, dto))
                .filter(utilisateur -> estFiltreEmail(utilisateur, dto))
                .filter(utilisateur -> estFiltreStatut(utilisateur, dto))
                .collect(Collectors.toList());
		List<Utilisateurdto> resultat = utilisateursResult.stream()
		        .map(this::convertToUtilisateurdto)
		        .collect(Collectors.toList());

		    return ResponseEntity.ok(resultat);
	}
	// Méthodes de filtre
    private boolean estFiltreNom(Utilisateur utilisateur, Utilisateurdto dto) {
        return dto.getNom() == null || utilisateur.getNom().equals(dto.getNom());
    }

    private boolean estFiltrePrenom(Utilisateur utilisateur, Utilisateurdto dto) {
        return dto.getPrenom() == null || utilisateur.getPrenom().equals(dto.getPrenom());
    }

    private boolean estFiltreNumTele(Utilisateur utilisateur, Utilisateurdto dto) {
        return dto.getNum_tele() == null || utilisateur.getNum_tele().equals(dto.getNum_tele());
    }

    private boolean estFiltreEmail(Utilisateur utilisateur, Utilisateurdto dto) {
        return dto.getEmail() == null || utilisateur.getEmail().equals(dto.getEmail());
    }

    private boolean estFiltreStatut(Utilisateur utilisateur, Utilisateurdto dto) {
        return dto.getStatut() == null || utilisateur.getStatut().equals(dto.getStatut());
    }
}
