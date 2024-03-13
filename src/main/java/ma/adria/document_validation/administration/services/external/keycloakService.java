package ma.adria.document_validation.administration.services.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public interface keycloakService {
	public ResponseEntity<String> GetTokenFromKeycloakWithClient_credentials(String client_secret, String keycloakUrl) ;
	public void addUserToKeycloak(String username, String email, String password, String bearerToken,
			String KEYCLOAK_URL,String firstname,String lastname) ;
	public String extractTokenFromResponse(ResponseEntity<String> response);
}
