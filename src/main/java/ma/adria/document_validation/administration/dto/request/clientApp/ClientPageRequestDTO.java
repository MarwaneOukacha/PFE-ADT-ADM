package ma.adria.document_validation.administration.dto.request.clientApp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.adria.document_validation.administration.dto.PageableDTO;
import ma.adria.document_validation.administration.model.enums.clientStatus;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientPageRequestDTO extends PageableDTO {
    private String companyName;
    private clientStatus statut;
    private String name;
}
