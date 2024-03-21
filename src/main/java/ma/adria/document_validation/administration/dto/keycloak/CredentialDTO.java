package ma.adria.document_validation.administration.dto.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter @NoArgsConstructor 
@AllArgsConstructor @Builder
@Component
public class CredentialDTO {
	private String type;
    private String value;
    private boolean temporary;
}
