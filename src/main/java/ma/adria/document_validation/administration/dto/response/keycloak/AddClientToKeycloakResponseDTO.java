package ma.adria.document_validation.administration.dto.response.keycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder(toBuilder = true)
public class AddClientToKeycloakResponseDTO {
    private String keycloakID;
    private String secret;
}
