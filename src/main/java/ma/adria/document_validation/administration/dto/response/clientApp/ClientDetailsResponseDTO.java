package ma.adria.document_validation.administration.dto.response.clientApp;

import lombok.*;
import ma.adria.document_validation.administration.model.enums.clientStatus;

import javax.persistence.Column;
import java.util.UUID;

@Builder(toBuilder = true)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ClientDetailsResponseDTO {
    private String secret;
    protected UUID id;
    private String codeApp;
    private clientStatus statut;
    private String name;
    private int nbrMaxTransactions;
    private int sizeMax;
    private  String companyName;
}
