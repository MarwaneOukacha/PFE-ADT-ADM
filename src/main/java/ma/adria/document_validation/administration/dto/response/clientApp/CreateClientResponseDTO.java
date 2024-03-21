package ma.adria.document_validation.administration.dto.response.clientApp;

import lombok.*;
import ma.adria.document_validation.administration.model.enums.clientStatus;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientResponseDTO {
    private String id;
    private String companyName;
    private String codeApp;
    private clientStatus statut;
    private String name;
    private int sizeMax;
    private int nbrMaxTransactions;
}
