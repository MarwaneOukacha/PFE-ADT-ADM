package ma.adria.document_validation.administration.services.adtconstants;

import ma.adria.document_validation.administration.dto.request.ADTConst.AdtConstPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.CreateADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.EditADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstCodeResponseDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IADTConstService {

    ADTConstResponseDTO getADTConstById(String id);

    List<ADTConstResponseDTO> getAllADTConst();

    ADTConstResponseDTO createADTConst(CreateADTConstRequestDTO createAdtConstRequestDTO);

    ADTConstResponseDTO updateADTConst(EditADTConstRequestDTO editADTConstRequestDTO);

    List<ADTConstCodeResponseDTO> getAllADTConstCodes();

    Page<ADTConstResponseDTO> getADTConstPage(AdtConstPageRequestDTO adtConstPageRequestDTO);


}
