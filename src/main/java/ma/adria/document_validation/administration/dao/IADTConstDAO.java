package ma.adria.document_validation.administration.dao;

import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface IADTConstDAO {

    boolean existsByCode(ADTConstCode code);

    ADTConst save(ADTConst adtConst);

    ADTConst findById(UUID id);

    ADTConst findADTConstByCode(ADTConstCode code);

    List<ADTConst> findAll();

    Page<ADTConst> findADTConstPage(Specification<ADTConst> specification, Pageable pageable);

    ADTConst getADTConstValueByCode(ADTConstCode code);

}