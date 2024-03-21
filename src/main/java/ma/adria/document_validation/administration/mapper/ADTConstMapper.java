package ma.adria.document_validation.administration.mapper;

import ma.adria.document_validation.administration.dto.request.ADTConst.CreateADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.request.ADTConst.EditADTConstRequestDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstCodeResponseDTO;
import ma.adria.document_validation.administration.dto.response.ADTConst.ADTConstResponseDTO;
import ma.adria.document_validation.administration.model.entities.ADTConst;
import org.mapstruct.*;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public abstract class ADTConstMapper {

    public abstract ADTConstResponseDTO mapADTConstToADTConstResponseDTO(ADTConst adtConst);

    public abstract ADTConst mapCreateADTConstRequestDTOtoADTConst(CreateADTConstRequestDTO createAdtConstRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "code", ignore = true)
    public abstract void updateADTConstFromADTConstRequestDTO(@MappingTarget ADTConst adtConst, EditADTConstRequestDTO editADTConstRequestDTO);

    public abstract List<ADTConstResponseDTO> mapAdtConstListToADTConstResponseDTOList(List<ADTConst> adtConstList);

    public abstract List<ADTConstCodeResponseDTO> mapAdtConstListToADTConstCodeResponseDTOList(List<ADTConst> adtConstList);

    public abstract ADTConstCodeResponseDTO mapAdtConstToADTConstCodeResponseDTO(ADTConst adtConst);

}