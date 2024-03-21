package ma.adria.document_validation.administration.dto.response.clientApp;

import lombok.Builder;
import ma.adria.document_validation.administration.model.enums.clientStatus;
@Builder(toBuilder = true)
public class ClientNameResponseDTO {
    private String id;
    private String companyName;
    private clientStatus status;
}
