package ma.adria.document_validation.administration.mapper;

import ma.adria.document_validation.administration.dto.keycloak.CredentialDTO;
import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.user.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.response.user.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.UserPageResponseDTO;
import org.mapstruct.*;

import ma.adria.document_validation.administration.dto.keycloak.KeycloakUserDTO;
import ma.adria.document_validation.administration.model.entities.Utilisateur;

import java.util.Collections;

@Mapper(builder = @Builder(disableBuilder = true),
        componentModel = "spring")
public interface UtilisateurMapper {

    @Mapping(source = "utilisateur.prenom", target = "firstName")
    @Mapping(source = "utilisateur.nom", target = "lastName")
    @Mapping(source = "utilisateur.email", target = "username")
    @Mapping(target = "enabled", constant = "true")
    KeycloakUserDTO toKeycloakUserDTO(Utilisateur utilisateur, String password);

    @AfterMapping
    default void afterMappingUtilisateurToKeycloakUserDTO(@MappingTarget KeycloakUserDTO keycloakUserDTO, String password) {
        keycloakUserDTO.setCredentials(Collections.singletonList(
                CredentialDTO.builder().type("password").value(password).temporary(false).build()));
    }

    @Mapping(source = "prenom", target = "firstName")
    @Mapping(source = "nom", target = "lastName")
    @Mapping(source = "email", target = "username")
    KeycloakUserDTO toKeycloakUserDTO(CreateUserRequestDTO utilisateurDTO);

    Utilisateur toUtilisateur(CreateUserRequestDTO utilisateurDTO);

    CreateUserResponseDTO toCreateUtilisateurResponseDTO(Utilisateur utilisateur);

    public abstract UserPageResponseDTO mapUserToUserSearchResponseDTO(Utilisateur user);

    UtilisateurDTO toUtilisateurDTO(Utilisateur user);

    EditUserResponseDTO toEditUtilisateurResponseDTO(Utilisateur user);

    EditUserProfileResponseDTO toEditUtilisateurProfileResponseDTO(Utilisateur user);
}
