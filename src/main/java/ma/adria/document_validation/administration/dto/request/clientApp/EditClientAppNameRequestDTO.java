package ma.adria.document_validation.administration.dto.request.clientApp;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder(toBuilder = true)
public class EditClientAppNameRequestDTO {
    private String clientId;
    private final boolean enabled=true;
    private List<String> redirectUris;
    private List<String> webOrigins;
}
