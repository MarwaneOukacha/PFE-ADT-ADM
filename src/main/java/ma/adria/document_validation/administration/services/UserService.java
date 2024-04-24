package ma.adria.document_validation.administration.services;

import ma.adria.document_validation.administration.dto.request.user.ResetPasswordRequestDTO;
import ma.adria.document_validation.administration.dto.request.user.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.user.EditUserProfileRequestDTO;
import ma.adria.document_validation.administration.dto.request.user.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.user.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.user.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.user.UserPageResponseDTO;
import ma.adria.document_validation.administration.model.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import ma.adria.document_validation.administration.dto.UtilisateurDTO;


public interface UserService {
	public ResponseEntity<CreateUserResponseDTO> createUtilisateur(CreateUserRequestDTO utilisateur);
	Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO);
	EditUserProfileResponseDTO editProfile(EditUserProfileRequestDTO user);
	void resetPassword(ResetPasswordRequestDTO request);
	UtilisateurDTO getUserById(String id);
	UtilisateurDTO getUserByKeycloakId(String id);

    EditUserResponseDTO editUser(EditUserRequestDTO user);

	UtilisateurDTO getCurrentUserDetails();
}
