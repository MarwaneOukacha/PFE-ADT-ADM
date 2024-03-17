package ma.adria.document_validation.administration.mapper;

import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.model.entities.Utilisateur;

@Mapper(builder = @Builder(disableBuilder = true),
        componentModel = "spring") 
public interface UtilisateurMapper {
	@Mapping(source = "prenom", target = "firstName")
    @Mapping(source = "nom", target = "lastName")
	@Mapping(source = "email", target = "username")
	UtilisateurKycDTO toUtilisateurKycDTO(Utilisateur utilisateur);
	@Mapping(source = "prenom", target = "firstName")
	@Mapping(source = "nom", target = "lastName")
	@Mapping(source = "email", target = "username")
	UtilisateurKycDTO toUtilisateurKycDTO(CreateUserRequestDTO utilisateurDTO);
	Utilisateur toUtilisateur(CreateUserRequestDTO utilisateurDTO);
	CreateUserResponseDTO toCreateUtilisateurResponseDTO(CreateUserRequestDTO utilisateurDTO);
	public abstract UserPageResponseDTO mapUserToUserSearchResponseDTO(Utilisateur user);

	UtilisateurDTO toUtilisateurDTO(Utilisateur user);
	EditUserResponseDTO toEditUtilisateurResponseDTO(Utilisateur user);
	EditUserProfileResponseDTO toEditUtilisateurProfileResponseDTO(Utilisateur user);
}
