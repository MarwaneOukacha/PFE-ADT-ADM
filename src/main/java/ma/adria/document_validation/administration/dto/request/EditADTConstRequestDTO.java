package ma.adria.document_validation.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditADTConstRequestDTO {

    @NotEmpty
    private String id;

    @NotEmpty
    private String value;

    private Boolean encrypted;

}
