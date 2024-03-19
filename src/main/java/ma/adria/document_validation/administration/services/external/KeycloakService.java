package ma.adria.document_validation.administration.services.external;

import org.springframework.http.ResponseEntity;

import ma.adria.document_validation.administration.dto.KeycloakUserDTO;

public interface KeycloakService {
	public String addUserToKeycloak(KeycloakUserDTO dto);
	public String extractTokenFromResponse(ResponseEntity<String> response);
	public String getUserKeycloakIdFromResponse(ResponseEntity<String> response);
	public void updatePassword(String password);
	public void updateUsername(String newUsername);
}
