package ma.adria.document_validation.administration.services.external.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ma.adria.document_validation.administration.dto.keycloak.*;
import ma.adria.document_validation.administration.dto.request.clientApp.EditClientAppNameRequestDTO;
import ma.adria.document_validation.administration.dto.response.keycloak.RoleDtoKeycloakResponse;
import ma.adria.document_validation.administration.dto.response.clientApp.EditClientResponseDTO;
import ma.adria.document_validation.administration.dto.response.keycloak.AddClientToKeycloakResponseDTO;
import ma.adria.document_validation.administration.dto.response.keycloak.keycloakSecretResponseDTO;
import ma.adria.document_validation.administration.services.external.KeycloakService;
import ma.adria.document_validation.administration.util.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class keycloakServiceImpl implements KeycloakService {

    @Value("${app.key_cloak.client_id}")
    private String clientId;

    private final RestTemplate restTemplate;

    @Value("${app.key_cloak.client_secret}")
    private String clientSecret;
    @Value("${app.key_cloak.keycloak_client_id}")
    private String keycloakClientId;


    @Value("${app.key_cloak.users}")
    private String keycloakUrlUser;

    @Value("${app.key_cloak.auth}")
    private String keycloakUrlToken;
    @Value("${app.key_cloak.clients}")
    private String keycloakUrlClients;

    private final UserUtils utils;

    private ResponseEntity<String> getTokenFromKeycloakWithClientCredentials() {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=client_credentials&client_secret=" + clientSecret
                + "&client_id=" + clientId;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(keycloakUrlToken, request, String.class);
    }

    @Override
    public String addUserToKeycloak(KeycloakUserDTO dto) {

        HttpHeaders headers = setTokenAuthorizationHeaders();

        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = "";

        try {

            requestBody = objectMapper.writeValueAsString(dto);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage(), e);

        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrlUser, request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("Utilisateur créé avec succès ! ");
            return getUserKeycloakIdFromResponse(response);

        } else {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur : " + response.getBody());

        }
    }

    @Override
    public void updatePassword(String newPassword) {
        HttpHeaders headers = setTokenAuthorizationHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = "";
        PasswordUpdateKeycloakDTO password = new PasswordUpdateKeycloakDTO();
        password.setValue(newPassword);
        try {
            requestBody = objectMapper.writeValueAsString(password);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage(), e);
        }
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        String updatePasswordUrl = keycloakUrlUser + "/" + utils.getCurrentUser().getKeycloakId() + "/reset-password";
        ResponseEntity<String> response = restTemplate.exchange(
                updatePasswordUrl,
                HttpMethod.PUT,
                request,
                String.class
        );
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("Utilisateur modifié avec succès ! ");
        } else {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur : " + response.getBody());
        }
    }

    @Override
    public String getUserKeycloakIdFromResponse(ResponseEntity<String> response) {
        String path = response.getHeaders().getLocation().getPath();
        String KeycloakID = path.substring(path.lastIndexOf('/') + 1);
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
            throw new RuntimeException("Une erreur s'est produite lors de l'extraction du token : " + e.getMessage(), e);
        }
    }

    @Override
    public void updateUsername(String newUsername) {
        HttpHeaders headers = setTokenAuthorizationHeaders();
        String updateUsernameUrl = keycloakUrlUser + "/" + utils.getCurrentUser().getKeycloakId();


        UsernameUpdateKeycloakDTO usernameDTO = new UsernameUpdateKeycloakDTO();
        usernameDTO.setUsername(newUsername);


        String requestBody;
        try {
            requestBody = new ObjectMapper().writeValueAsString(usernameDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage(), e);
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
            throw new RuntimeException("keycloak: Erreur lors de la mise à jour du nom d'utilisateur : " + response.getBody());
        }
    }

    @Override
    public void updateClientAppName(EditClientAppNameRequestDTO editClientRequestDTO,String id) {
        HttpHeaders headers = setTokenAuthorizationHeaders();

        HttpEntity<EditClientAppNameRequestDTO> request = new HttpEntity<>(editClientRequestDTO, headers);

        String url = keycloakUrlClients+"/"+id;

        ResponseEntity<EditClientResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                EditClientResponseDTO.class
        );

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("Client app name updated");
        } else {
            throw new RuntimeException("Failed to update client name: " + response.getStatusCode());
        }
    }
    @Override
    public AddClientToKeycloakResponseDTO addClientToKeycloak(KeycloakClientDTO clientDto) {
        HttpHeaders headers = setTokenAuthorizationHeaders();

        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = "";

        try {

            requestBody = objectMapper.writeValueAsString(clientDto);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur lors de la conversion de l'objet en JSON : " + e.getMessage(), e);

        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(keycloakUrlClients, request, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            log.info("client créé avec succès!");
            String keycloakID=getUserKeycloakIdFromResponse(response);
            String secret= getClientSecret(keycloakID);
            return AddClientToKeycloakResponseDTO.builder()
                    .keycloakID(keycloakID)
                    .secret(secret)
                    .build();

        } else {
            throw new RuntimeException("Erreur lors de la création du client : " + response.getBody());

        }
    }

    @Override
    public void updateClientName(String newClientName) {

    }

    @Override
    public String getClientSecret(String keycloakID) {
        HttpHeaders headers = setTokenAuthorizationHeaders();
        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = "{\n" +
                "  \"value\": \"new_generated_secret\",\n" +
                "  \"expiration\": 0,\n" +
                "  \"generated\": true\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<keycloakSecretResponseDTO> response = restTemplate.postForEntity(keycloakUrlClients+"/"+keycloakID+"/client-secret", request, keycloakSecretResponseDTO.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("client secret créé avec succès!");
            return response.getBody().getValue();
        } else {
            throw new RuntimeException("Erreur lors de la création du secret : " + response.getBody());

        }
    }

    @Override
    public ResponseEntity<String> getRole(String roleName) {
        HttpHeaders headers = setTokenAuthorizationHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Imprime l'URL de la requête pour le débogage
        String requestUrl = keycloakUrlClients + "/ec335d53-dc1d-45c7-b7f2-f632c969c9db/roles/" + roleName;
        System.out.println("Request URL: " + requestUrl);

        // Utilisation de RestTemplate exchange pour une flexibilité accrue
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Succès!");
                return response;
            } else {
                throw new RuntimeException("Erreur : " + response.getBody());
            }
        } catch (Exception e) {

            throw new RuntimeException("Une erreur s'est produite lors de la requête : " + e.getMessage());


        }
    }

    @Override
    public void AssignRoleToUser(String keycloakUserID, String roleName) {
        HttpHeaders headers = setTokenAuthorizationHeaders();
        ResponseEntity<String> res = getRole(roleName);
        ObjectMapper objectMapper = new ObjectMapper();

        String requestBody = "";
        try {
            String jsonRole = res.getBody();
            RoleDtoKeycloakResponse roleAssignment = objectMapper.readValue(jsonRole, RoleDtoKeycloakResponse.class);

            requestBody = objectMapper.writeValueAsString(roleAssignment);
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de la conversion de l'objet en JSON :" + e.getMessage());
        }

        System.out.println("[" + requestBody + "]");
    HttpEntity<String> request = new HttpEntity<>("[" + requestBody + "]", headers);
    String requestURL = keycloakUrlUser + "/" + keycloakUserID + "/role-mappings/clients/" + keycloakClientId;
    ResponseEntity<String> response = restTemplate.exchange(
            requestURL,
            HttpMethod.POST,
            request, String.class);
    if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
        log.info("Role assigned!");
    } else {
        throw new RuntimeException("Erreur : " + response.getBody());
    }
    }


    private HttpHeaders setTokenAuthorizationHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String bearerToken = extractTokenFromResponse(getTokenFromKeycloakWithClientCredentials());
        headers.setBearerAuth(bearerToken);
        return headers;
    }
}
