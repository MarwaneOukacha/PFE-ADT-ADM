package ma.adria.document_validation.administration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserResponseDTO {
    private String prenom;

    private String nom;

    private String numTele;

    private String email;

    private String statut;

    private int nbrMaxTransactions;

    private int sizeMax;
}
