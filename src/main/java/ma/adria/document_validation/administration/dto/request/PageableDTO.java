package ma.adria.document_validation.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {

    @NotEmpty
    private int page;

    @NotEmpty
    private int size;

    private String sort;

}