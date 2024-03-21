package ma.adria.document_validation.administration.dto.request.clientApp;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
@Getter @Setter
public class CreateClientRequestDTO {
    private String clientId;
    private List<String> redirectUris;
    @NotEmpty
    private String companyName;
    @NotEmpty
    private String name;
}
