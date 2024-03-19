package ma.adria.document_validation.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequestDTO {

    private String prenom;

    private String nom;

    private String numTele;

    private String email;
    private String oldEmail;

    private String statut;

    private String password ;

    private int nbrMaxTransactions;

    private int sizeMax;
}
