package ma.adria.document_validation.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableDTO {
    @Min(0)
   private int page;

    @Min(1)
    private int size;

    private String sort;

}
