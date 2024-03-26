package ma.adria.document_validation.administration.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class KeycloakClientDTO {
    private String clientId;
    private final boolean serviceAccountsEnabled=true;
    private final boolean enabled=true;
    private final boolean publicClient=false;
    private List<String> redirectUris;
    private final String protocol="openid-connect";
    private final String secret="fggg";
    private final String clientAuthenticatorType="client-secret";
}
