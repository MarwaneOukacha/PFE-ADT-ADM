package ma.adria.document_validation.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdtConstPageRequestDTO extends PageableDTO {

    private String code;

    private String value;

    private Boolean encrypted;

}

