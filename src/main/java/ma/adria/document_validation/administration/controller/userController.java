package ma.adria.document_validation.administration.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import ma.adria.document_validation.administration.dto.Utilisateurdto;
import ma.adria.document_validation.administration.exception.UserException;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.services.UserService;

@RestController
@RequestMapping("/users")
public class userController {
	@Autowired
	private UserService service;
	// Endpoint pour créer un utilisateur

	@PostMapping("/")
	public ResponseEntity<Utilisateurdto> createUtilisateur(@RequestBody Utilisateur utilisateur) {
		try {
			return service.createUtilisateur(utilisateur);
		} catch (Exception e) {
			throw new UserException("Error: "+e.getMessage());
		}
	}

	// Endpoint pour récupérer tous les utilisateurs

	@GetMapping("/")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateurs() {
		return service.getAllUtilisateurs();
	}

	// Endpoint pour récupérer un utilisateur par ID
	@GetMapping("/{id}")
	public ResponseEntity<Utilisateurdto> getUtilisateurById(@PathVariable("id") long id) {
		return service.getUtilisateurById(id);
	}

	// Endpoint pour mettre à jour un utilisateur
	@PutMapping("/{email}")
	public ResponseEntity<Utilisateurdto> updateUtilisateur(@PathVariable("email") String email,
			@RequestBody Utilisateur utilisateur) {
		return service.updateUtilisateur(email, utilisateur);
	}


	// Endpoint pour modifier le nbr de transactions
	@PutMapping("/nombreTransactions/{email}")
	public ResponseEntity<HttpStatus> updateNbrTransactionUtilisateur(@PathVariable("email") String email,
			@RequestBody  Utilisateurdto user) {
		
		return service.updateNbrTransactionUtilisateur(email, user.getNbr_max_transactions());
	}
	// Endpoint pour bloquer un utilisateur

	@PutMapping("/bloquer/{email}")
	public ResponseEntity<HttpStatus> bloquertUtilisateur(@PathVariable("email") String email) {
		return service.bloquerUtilisateur(email);
	}
	// Endpoint pour debloquer un utilisateur
	
	@PutMapping("/debloquer/{email}")
	public ResponseEntity<HttpStatus> debloquertUtilisateur(@PathVariable("email") String email) {
		return service.debloquerUtilisateur(email);
	}
	
	// Endpoint pour activer un utilisateur
	
	@PutMapping("/activer/{email}")
	public ResponseEntity<HttpStatus> activerUtilisateur(@PathVariable("email") String email) {
		return service.activerUtilisateur(email);
	}
	// Endpoint pour desactiver un utilisateur

	@PutMapping("/desactiver/{email}")
	public ResponseEntity<HttpStatus> desactiverUtilisateur(@PathVariable("email") String email) {
		return service.desactiverUtilisateur(email);
	}
	@GetMapping("/nom/{nom}")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByNom(@PathVariable String nom){
		return service.getAllUtilisateursByNom(nom);
	}
	@GetMapping("/prenom/{nom}")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursByPrenom(@PathVariable String nom){
		return service.getAllUtilisateursByPrenom(nom);
	}
	@GetMapping("/chercher/bloquer")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursBloque(){
		return service.getAllUtilisateursBloque();
	}
	@GetMapping("/chercher/debloquer")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDebloque(){
		return service.getAllUtilisateursDeBloque();
	}
	@GetMapping("/chercher/activer")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursActiver(){
		return service.getAllUtilisateursActiver();
	}
	@GetMapping("/chercher/desactiver")
	public ResponseEntity<List<Utilisateurdto>> getAllUtilisateursDesactiver(){
		return service.getAllUtilisateursDesactiver();
	}
    // La recherche multicritères

	@GetMapping("/chercher/multicritères")
	public ResponseEntity<List<Utilisateurdto>> rechercherUtilisateur(@RequestBody Utilisateurdto dto){
		return service.rechercherUtilisateurMulticriteres(dto);
	}
}
