package ma.adria.document_validation.administration.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class keycloakServiceV01 implements keycloakService{

    @Value("${app.key_cloak.client_id}")
	private String client_id;
    @Autowired
	private RestTemplate restTemplate;
    
    @Override
	public ResponseEntity<String> GetTokenFromKeycloakWithClient_credentials(String client_secret, String keycloakUrl) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String requestBody = "grant_type=client_credentials&client_secret=" + client_secret
				+ "&client_id="+client_id;

		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrl, request, String.class);

		return response;
	}
	@Override
	public void addUserToKeycloak(String username, String email, String password, String bearerToken,
			String KEYCLOAK_URL,String firstname,String lastname) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(bearerToken);

		/*String requestBody = "{" + "\"username\": \"" + username + "\"," + "\"email\": \"" + email + "\","
				+ "\"enabled\": true," + "\"emailVerified\": false," + "\"credentials\": [{"
				+ "    \"type\": \"password\"," + "    \"value\": \"" + password + "\"," + "    \"temporary\": false"
				+ "}]" + "}";*/
		String requestBody = "{" +
                "\"username\": \"" + username + "\"," +
                "\"email\": \"" + email + "\"," +
                "\"enabled\": true," +
                "\"emailVerified\": false," +
                "\"firstName\": \"" + firstname + "\"," +
                "\"lastName\": \"" + lastname + "\"," +
                "\"credentials\": [{" +
                "    \"type\": \"password\"," +
                "    \"value\": \"" + password + "\"," +
                "    \"temporary\": false" +
                "}]" +
                "}";

		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(KEYCLOAK_URL, request, String.class);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Utilisateur créé avec succès !");
		} else {
			System.out.println("Erreur lors de la création de l'utilisateur : " + response.getBody());
		}
	}
	@Override
	public String extractTokenFromResponse(ResponseEntity<String> response) {

		try {
			// Créez un ObjectMapper
			ObjectMapper mapper = new ObjectMapper();

			// Convertissez la réponse en un nœud JSON
			JsonNode responseJson = mapper.readTree(response.getBody());

			// Récupérez la valeur du champ "access_token"
			String accessToken = responseJson.get("access_token").asText();

			// Retournez le token d'authentification
			return accessToken;
		} catch (Exception e) {
			// Gérez les erreurs de parsing JSON ici
			e.printStackTrace();
			return null;
		}
	}
}
