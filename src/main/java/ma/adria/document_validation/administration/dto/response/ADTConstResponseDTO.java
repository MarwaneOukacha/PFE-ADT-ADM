package ma.adria.document_validation.administration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ADTConstResponseDTO {

    private String id;

    private String code;

    private String value;

    private boolean encrypted;

}
