package ma.adria.document_validation.administration.config.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ma.adria.document_validation.administration.exception.KycException;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.userProfile;
import ma.adria.document_validation.administration.model.enums.userStatus;
import ma.adria.document_validation.administration.reposetiry.UserReposetory;
import ma.adria.document_validation.administration.services.external.keycloakService;
@Configuration
public class userRunner {
	@Autowired private PasswordEncoder passwordencoder;
	@Autowired
	private keycloakService kyckService;
	
    @Value("${app.key_cloak.client_secret}")
	private String client_secret;
    
    @Value("${app.key_cloak.users}")
	private String keycloakUrlUser;
    
    @Value("${app.key_cloak.auth}")
	private String keycloakUrlToken;
    

	@Bean
	public CommandLineRunner commandLineRunner(UserReposetory userRepository) {

		return args -> {
			try {
				if(userRepository.existsByEmail("admin@gmail.com")==false) {
					String token=kyckService.extractTokenFromResponse(kyckService.GetTokenFromKeycloakWithClient_credentials(client_secret, keycloakUrlToken));
					System.out.println("token=="+token);
					kyckService.addUserToKeycloak("admin@gmail.com","admin@gmail.com","admin",token,keycloakUrlUser,"admin","admin");
					System.out.println("ajouté");
					Utilisateur user = new Utilisateur();
					user.setEmail("admin@gmail.com");
					user.setProfil(userProfile.admin.toString());
					user.setStatut(userStatus.activer.toString());
					user.setNom("admin");
					user.setPrenom("admin");
					user.setKeycloak_id("######");
					user.setNum_tele("#########");
					user.setNbr_max_transactions(0);
					user.setSize_max(0);
					user.setPassword(passwordencoder.encode("admin"));
					userRepository.save(user);
				}
			}catch(Exception e) {
				throw new KycException();
			}
			
		};
	}

}
