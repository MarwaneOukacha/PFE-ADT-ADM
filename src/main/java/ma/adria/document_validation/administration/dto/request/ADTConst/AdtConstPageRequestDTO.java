package ma.adria.document_validation.administration.dto.request.ADTConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.adria.document_validation.administration.dto.request.PageableDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdtConstPageRequestDTO extends PageableDTO {

    private String code;

    private String value;

    private Boolean encrypted;

}

