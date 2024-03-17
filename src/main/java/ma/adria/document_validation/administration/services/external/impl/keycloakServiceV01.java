package ma.adria.document_validation.administration.services.external.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dto.PasswordUpdateKycloakDTO;
import ma.adria.document_validation.administration.dto.UsernameUpdateKycloakDTO;
import ma.adria.document_validation.administration.dto.UtilisateurKycDTO;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import ma.adria.document_validation.administration.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;


@Service
@Getter @Setter
@RequiredArgsConstructor
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
	private final UserUtils utils;
    
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
	public void updatePassword(String newPassword) {
		HttpHeaders headers= setTokenAuthorizationHeaders();
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody="";
		PasswordUpdateKycloakDTO password= new PasswordUpdateKycloakDTO();
		password.setValue(newPassword);
		try {
			requestBody = objectMapper.writeValueAsString(password);
		} catch (JsonProcessingException e) {
			System.err.println("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage());
		}
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		String updatePasswordUrl = keycloakUrlUser+"/" +utils.getCurrentUser().getKeycloakId() + "/reset-password";
		ResponseEntity<String> response = restTemplate.exchange(
				updatePasswordUrl,
				HttpMethod.PUT,
				request,
				String.class
		);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			System.out.println("Utilisateur modifié avec succès ! ");

		} else {
			System.out.println("Erreur lors de la création de l'utilisateur : " + response.getBody());

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
	@Override
	public void updateUsername(String newUsername) {
		HttpHeaders headers = setTokenAuthorizationHeaders();
		String updateUsernameUrl = keycloakUrlUser + "/" + utils.getCurrentUser().getKeycloakId();


		UsernameUpdateKycloakDTO usernameDTO = new UsernameUpdateKycloakDTO();
		usernameDTO.setUsername(newUsername);


		String requestBody;
		try {
			requestBody = new ObjectMapper().writeValueAsString(usernameDTO);
		} catch (JsonProcessingException e) {
			System.err.println("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage());
			return; // Handle the exception appropriately
		}


		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		// Use RestTemplate to send the PUT request
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				updateUsernameUrl,
				HttpMethod.PUT,
				request,
				String.class
		);

		// Check the response status
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
			System.out.println("keycloak: Nom d'utilisateur mis à jour avec succès!");
		} else {
			System.err.println("keycloak: Erreur lors de la mise à jour du nom d'utilisateur : " + response.getBody());
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
