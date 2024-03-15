package ma.adria.document_validation.administration.services.external.impl;

import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;


@Service
@Getter @Setter 
public class keycloakServiceV01 implements KeycloakService {

    @Value("${app.key_cloak.client_id}")
	private String clientId;
    @Autowired
	private RestTemplate restTemplate;
    @Value("${app.key_cloak.client_secret}")
	private String clientSecret;     //inside keycloakServie
    
    @Value("${app.key_cloak.users}")
	private String keycloakUrlUser;
    
    @Value("${app.key_cloak.auth}")
	private String keycloakUrlToken;
    
    
    @Override
	public ResponseEntity<String> getTokenFromKeycloakWithClient_credentials() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String requestBody = "grant_type=client_credentials&client_secret=" + clientSecret
				+ "&client_id="+clientId;
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrlToken, request, String.class);
		return response;
	}
	@Override
	public String addUserToKeycloak(UtilisateurKycDTO dto) throws JsonProcessingException {
		HttpHeaders headers= setTokenAuthorizationHeaders();
	    ObjectMapper objectMapper = new ObjectMapper();
	    String requestBody="";
	    try {
	        requestBody = objectMapper.writeValueAsString(dto);
	    } catch (JsonProcessingException e) {
	        System.err.println("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage());
	    }
	    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
	    ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrlUser, request, String.class);
	    if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Utilisateur créé avec succès ! ");
			return getUserKeycloakIdFromResponse(response);
	    } else {
	        System.out.println("Erreur lors de la création de l'utilisateur : " + response.getBody());
			return null;
	    }
	}

    @Override
    public String getUserKeycloakIdFromKeycloak(UtilisateurKycDTO dto) {
        return null;
    }

    @Override
	public String getUserKeycloakIdFromResponse(ResponseEntity<String> response) {
        String path=response.getHeaders().getLocation().getPath();
        String KeycloakID=path.substring(path.lastIndexOf('/') + 1);
		return KeycloakID;
	}

	@Override
	public String extractTokenFromResponse(ResponseEntity<String> response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode responseJson = mapper.readTree(response.getBody());
			String accessToken = responseJson.get("access_token").asText();
			return accessToken;
		} catch (Exception e) {
			// Gérez les erreurs de parsing JSON ici
			e.printStackTrace();
			return null;
		}
	}

	private HttpHeaders setTokenAuthorizationHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String bearerToken= extractTokenFromResponse(getTokenFromKeycloakWithClient_credentials());
		headers.setBearerAuth(bearerToken);
		return  headers;
	}
}
