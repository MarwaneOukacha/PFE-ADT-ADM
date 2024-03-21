package ma.adria.document_validation.administration.dto.response.keycloak;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class keycloakSecretResponseDTO {
    private String type;
    private String value;
}
