package ma.adria.document_validation.administration.dto.keycloak;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class    KeycloakUserDTO {

    private String username;

    private String email;

    private boolean enabled;

    private boolean emailVerified;

    private String firstName;

    private String lastName;

    private List<CredentialDTO> credentials;

}
