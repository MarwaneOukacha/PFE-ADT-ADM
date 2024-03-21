package ma.adria.document_validation.administration.dto.response.clientApp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EditClientResponseDTO {
    private String id;
    private String statut;
    private String name;
    private int nbrMaxTransactions;
    private int sizeMax;
    private  String companyName;
}