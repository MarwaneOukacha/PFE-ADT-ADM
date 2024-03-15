package ma.adria.document_validation.administration.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Date;
@Getter
@Setter
public class CreateUserRequestDTO {
    @NotNull
    private String prenom;
    @NotNull
    private String nom;
    @NotNull
    private String numTele;
    @Email
    private String email;
    @NotNull
    private String password ;

}
