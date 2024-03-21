package ma.adria.document_validation.administration.services.adtconstants.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.dto.request.ADTConst.AdtConstPageRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.CreateADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.EditADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstCodeResponseDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstResponseDTO;
import ma.adria.document_validation.administration.mapper.ADTConstMapper;
import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.reposetiry.specifications.AdtConstSpecification;
import ma.adria.document_validation.administration.services.adtconstants.IADTConstService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ADTConstServiceImpl implements IADTConstService {

    private final IADTConstDAO adtConstDAO;

    private final ADTConstMapper adtConstMapper;

    private final AdtConstSpecification adtConstSpecification;



    @Override
    public ADTConstResponseDTO getADTConstById(String id) {

        ADTConst adtConst = adtConstDAO.findById(UUID.fromString(id));

        return adtConstMapper.mapADTConstToADTConstResponseDTO(adtConst);

    }

    @Override
    public List<ADTConstResponseDTO> getAllADTConst() {

        List<ADTConst> adtConstList = adtConstDAO.findAll();

        return adtConstMapper.mapAdtConstListToADTConstResponseDTOList(adtConstList);

    }

    @Override
    public ADTConstResponseDTO createADTConst(CreateADTConstRequestDTO createAdtConstRequestDTO) {

        ADTConst adtConst = adtConstMapper.mapCreateADTConstRequestDTOtoADTConst(createAdtConstRequestDTO);

        return adtConstMapper.mapADTConstToADTConstResponseDTO(adtConstDAO.save(adtConst));

    }

    @Override
    public ADTConstResponseDTO updateADTConst(EditADTConstRequestDTO editADTConstRequestDTO) {

        ADTConst adtConst = adtConstDAO.findById(UUID.fromString(editADTConstRequestDTO.getId()));

        adtConstMapper.updateADTConstFromADTConstRequestDTO(adtConst, editADTConstRequestDTO);

        adtConst = adtConstDAO.save(adtConst);

        return adtConstMapper.mapADTConstToADTConstResponseDTO(adtConst);

    }


    @Override
    public List<ADTConstCodeResponseDTO> getAllADTConstCodes() {

        List<ADTConst> adtConstList = adtConstDAO.findAll();
        return adtConstMapper.mapAdtConstListToADTConstCodeResponseDTOList(adtConstList);
    }


    @Override
    public Page<ADTConstResponseDTO> getADTConstPage(AdtConstPageRequestDTO adtConstPageRequestDTO) {

        Specification<ADTConst> adtConstSpec = getAdtConstSpecification(adtConstPageRequestDTO);

        final Page<ADTConst> adtConstPage = adtConstDAO.findADTConstPage(adtConstSpec, PageRequest.of(adtConstPageRequestDTO.getPage(), adtConstPageRequestDTO.getSize()));

        return adtConstPage.map(
                adtConstMapper::mapADTConstToADTConstResponseDTO
        );

    }



    private Specification<ADTConst> getAdtConstSpecification(AdtConstPageRequestDTO request) {

        Specification<ADTConst> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("id"));

        if (request.getCode() != null) {
            spec = spec.and(adtConstSpecification.codeLike(request.getCode()));
        }

        if (request.getValue() != null) {
            spec = spec.and(adtConstSpecification.valueLike(request.getValue()));
        }

        if (request.getEncrypted() != null) {
            spec = spec.and(adtConstSpecification.isEncrypted(request.getEncrypted()));
        }

        return spec;

    }

}