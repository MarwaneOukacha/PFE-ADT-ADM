package ma.adria.document_validation.administration.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import ma.adria.document_validation.administration.dto.request.CreateUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditUserRequestDTO;
import ma.adria.document_validation.administration.dto.request.UserPageRequestDTO;
import ma.adria.document_validation.administration.dto.response.CreateUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.EditUserResponseDTO;
import ma.adria.document_validation.administration.dto.response.UserPageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ma.adria.document_validation.administration.dto.UtilisateurDTO;
import ma.adria.document_validation.administration.model.entities.Utilisateur;


public interface UserService {
	public ResponseEntity<CreateUserResponseDTO> createUtilisateur(CreateUserRequestDTO utilisateur) throws JsonProcessingException;

	EditUserResponseDTO editUser(EditUserRequestDTO user);

	Page<UserPageResponseDTO> getPage(UserPageRequestDTO userPageRequestDTO);
}
