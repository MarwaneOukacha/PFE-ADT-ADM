package ma.adria.document_validation.administration.config.runners;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IUserDAO;
import ma.adria.document_validation.administration.dto.keycloak.KeycloakUserDTO;
import ma.adria.document_validation.administration.mapper.UtilisateurMapper;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import ma.adria.document_validation.administration.model.enums.UserProfile;
import ma.adria.document_validation.administration.model.enums.UserStatus;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;


@Configuration
@Transactional
@RequiredArgsConstructor
public class UserRunner implements CommandLineRunner {

    private final IUserDAO userDAO;

    private final PasswordEncoder passwordencoder;

    private final KeycloakService keycloakService;

    private final UtilisateurMapper utilisateurMapper;

    @Value("${app.entities.default.admin.password}")
    private String defaultUserPassword;

    @Value("${app.entities.default.admin.email}")
    private String defaultAdminEmail;

    @Value("${app.entities.default.admin.firstName}")
    private String defaultAdminFirstName;

    @Value("${app.entities.default.admin.lastName}")
    private String defaultAdminLastName;

    @Value("${app.entities.default.admin.phoneNumber}")
    private String defaultAdminPhoneNumber;


    private void createDefaultAdmin() {

        Utilisateur user = Utilisateur.builder()
                .email(defaultAdminEmail)
                .profil(UserProfile.ADMIN)
                .statut(UserStatus.ENABELED)
                .nom(defaultAdminLastName)
                .prenom(defaultAdminFirstName)
                .numTele(defaultAdminPhoneNumber)
                .nbrMaxTransactions(0)
                .sizeMax(0)
                .password(passwordencoder.encode(defaultUserPassword))
                .build();

        userDAO.save(user);

        KeycloakUserDTO keycloakUser = utilisateurMapper.toKeycloakUserDTO(user, defaultUserPassword);


        String keycloakId = keycloakService.addUserToKeycloak(keycloakUser);

        user.setKeycloakId(keycloakId);

        userDAO.save(user);

    }

    @Override
    public void run(String... args) {

        if (!userDAO.exists()) createDefaultAdmin();

    }
}
