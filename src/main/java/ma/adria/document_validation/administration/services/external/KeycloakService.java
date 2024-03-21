package ma.adria.document_validation.administration.services.external;

import ma.adria.document_validation.administration.dto.request.clientApp.EditClientAppNameRequestDTO;
import ma.adria.document_validation.administration.dto.response.keycloak.AddClientToKeycloakResponseDTO;
import ma.adria.document_validation.administration.dto.keycloak.KeycloakClientDTO;
import org.springframework.http.ResponseEntity;

import ma.adria.document_validation.administration.dto.keycloak.KeycloakUserDTO;

public interface KeycloakService {
	public String addUserToKeycloak(KeycloakUserDTO dto);
	public String extractTokenFromResponse(ResponseEntity<String> response);
	public String getUserKeycloakIdFromResponse(ResponseEntity<String> response);
	public void updatePassword(String password);
	public void updateUsername(String newUsername);
	public void updateClientAppName(EditClientAppNameRequestDTO editClientAppNameRequestDTO,String id);
	public AddClientToKeycloakResponseDTO addClientToKeycloak(KeycloakClientDTO clientDto);
	public void updateClientName(String newClientName);
	public String getClientSecret(String keycloakID);
}
