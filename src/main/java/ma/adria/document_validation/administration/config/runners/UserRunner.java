package ma.adria.document_validation.administration.config.runners;

import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.CredentialDTO;
import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ma.adria.document_validation.administration.services.external.KeycloakService;

import java.util.Arrays;


@Configuration
public class UserRunner {
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private KeycloakService kyckService;
	@Value("${app.entities.default.user.password}")
    private String defaultUserPassword ;
	@Autowired
    private UtilisateurMapper utilisateurMapper;
	
	@Bean
	//@Transactional(rollbackOn = Exception.class)
	public CommandLineRunner commandLineRunner(IUserDAO userDao) {

		return args -> {
			try {
				if (userDao.existsByUserName("admin@gmail.com") == false) {
					
					Utilisateur user = Utilisateur.builder()
							.email("admin@gmail.com")
							.profil(UserProfile.ADMIN)
							.statut(UserStatus.ENABELED)
							.nom("admin")
							.prenom("admin").numTele("#########") // Vous pouvez ajouter le numéro
																						// de téléphone ici
							.nbrMaxTransactions(0).sizeMax(0).password(passwordencoder.encode("admin")).build();
					userDao.save(user);
					UtilisateurKycDTO kycUser=utilisateurMapper.toUtilisateurKycDTO(user);

					kycUser=kycUser.toBuilder().enabled(true).credentials(Arrays.asList(CredentialDTO
									.builder().type("password").value(defaultUserPassword).temporary(false).build()))
							.build();
					String keyId=kyckService.addUserToKeycloak(kycUser);
					user=user.toBuilder().keycloakId(keyId).build();
					userDao.save(user);

				}
			} catch (Exception e) {
				e.getMessage();
			}

		};
	}

}
