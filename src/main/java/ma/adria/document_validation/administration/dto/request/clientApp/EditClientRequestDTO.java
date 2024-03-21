package ma.adria.document_validation.administration.dto.request.clientApp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EditClientRequestDTO {
    private String id;
    private String statut;
    private String name;
    private int nbrMaxTransactions;
    private int sizeMax;
    private  String companyName;
}
