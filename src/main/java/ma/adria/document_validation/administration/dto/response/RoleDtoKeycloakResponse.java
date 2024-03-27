package ma.adria.document_validation.administration.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleDtoKeycloakResponse {
    private String id;
    private String name;
    private Boolean composite;
    private Boolean clientRole;
    private String containerId;
    private Object attributes;
}
