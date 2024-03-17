package ma.adria.document_validation.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PasswordUpdateKycloakDTO {
    private final String type = "password";
    private final boolean temporary = false;
    private String value;
}
