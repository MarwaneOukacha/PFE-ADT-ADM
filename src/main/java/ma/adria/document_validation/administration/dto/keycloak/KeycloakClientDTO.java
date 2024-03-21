package ma.adria.document_validation.administration.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class KeycloakClientDTO {
    private String clientId;
    private final boolean enabled=true;
    private final boolean publicClient=true;
    private List<String> redirectUris;
    private final String protocol="openid-connect";
    private final Boolean bearerOnly=false;
    private final String clientAuthenticatorType="client-secret";
}
