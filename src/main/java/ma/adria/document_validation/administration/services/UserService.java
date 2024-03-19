package ma.adria.document_validation.administration.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import ma.adria.document_validation.administration.dto.request.ResetPasswordRequestDTO;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserProfileRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserProfileResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import ma.adria.document_validation.administration.dto.UtilisateurDTO;

import java.util.UUID;


public interface UserService {
	public ResponseEntity<CreateUserResponseDTO> createUtilisateur(CreateUserRequestDTO utilisateur);
	Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO);
	EditUserProfileResponseDTO editProfile(EditUserProfileRequestDTO user);
	void resetPassword(ResetPasswordRequestDTO request);
	UtilisateurDTO getUserById(String id);

    EditUserResponseDTO editUser(EditUserRequestDTO user);
}
