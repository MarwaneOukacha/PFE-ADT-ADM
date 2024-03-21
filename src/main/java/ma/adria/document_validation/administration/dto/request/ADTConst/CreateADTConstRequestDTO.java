package ma.adria.document_validation.administration.dto.request.ADTConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateADTConstRequestDTO {

    private String code;

    private String value;

    private boolean encrypted;

}
