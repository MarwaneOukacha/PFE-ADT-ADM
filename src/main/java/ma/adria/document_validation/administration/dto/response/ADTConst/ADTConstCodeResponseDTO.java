package ma.adria.document_validation.administration.dto.response.ADTConst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ADTConstCodeResponseDTO {

    private UUID id;

    private String code;

}