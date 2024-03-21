package ma.adria.document_validation.administration.dto.response.clientApp;

import lombok.*;
import ma.adria.document_validation.administration.model.enums.clientStatus;

import java.util.UUID;
@Builder(toBuilder = true) @Getter @Setter @NoArgsConstructor
@AllArgsConstructor
public class ClientPageResponseDTO {
    private UUID id;
    private String companyName;
    private clientStatus statut;
    private String name;
    private int nbrMaxTransactions;
    private int sizeMax;
}
