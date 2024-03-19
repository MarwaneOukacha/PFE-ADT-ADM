package ma.adria.document_validation.administration.services.adtconstants;

import ma.adria.document_validation.administration.dto.AdtConstPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.CreateADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.request.EditADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.response.ADTConstCodeResponseDTO;
import ma.adria.document_validation.administration.dto.response.ADTConstResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IADTConstService {

    ADTConstResponseDTO getADTConstById(String id);

    List<ADTConstResponseDTO> getAllADTConst();

    ADTConstResponseDTO createADTConst(CreateADTConstRequestDTO createAdtConstRequestDTO);

    ADTConstResponseDTO updateADTConst(EditADTConstRequestDTO editADTConstRequestDTO);

    List<ADTConstCodeResponseDTO> getAllADTConstCodes();

    Page<ADTConstResponseDTO> getADTConstPage(AdtConstPageRequestDTO adtConstPageRequestDTO);

    String isKeycloakExtern();
}
