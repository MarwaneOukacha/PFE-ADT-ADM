package ma.adria.document_validation.administration.services.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;

public interface KeycloakService {
	public ResponseEntity<String> getTokenFromKeycloakWithClient_credentials() ;
	public String addUserToKeycloak(UtilisateurKycDTO dto) throws JsonProcessingException;
	public String getUserKeycloakIdFromKeycloak(UtilisateurKycDTO dto) ;
	public String extractTokenFromResponse(ResponseEntity<String> response);
	public String getUserKeycloakIdFromResponse(ResponseEntity<String> response);
}
