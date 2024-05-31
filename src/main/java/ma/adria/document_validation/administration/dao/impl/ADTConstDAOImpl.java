package ma.adria.document_validation.administration.dao.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.document_validation.administration.dao.IADTConstDAO;
import ma.adria.document_validation.administration.exception.ResourceNotFoundException;
import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import ma.adria.document_validation.administration.reposetiry.ADTConstRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ADTConstDAOImpl implements IADTConstDAO {

    private final ADTConstRepository adtConstRepository;

    @Override
    public boolean existsByCode(ADTConstCode code) {

        return adtConstRepository.existsByCode(code);

    }

    @Override
    public ADTConst save(ADTConst adtConst) {

        return adtConstRepository.save(adtConst);

    }

    @Override
    public ADTConst findById(UUID id) {

        return adtConstRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ADT_CONST_NOT_FOUND_ID));

    }

    @Override
    public ADTConst findADTConstByCode(ADTConstCode code) {

        return adtConstRepository.findADTConstByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ADT_CONST_NOT_FOUND_CODE));

    }

    @Override
    public List<ADTConst> findAll() {

        List<ADTConst> adtConstList = new ArrayList<>();

        adtConstRepository.findAll().forEach(adtConstList::add);

        return adtConstList;

    }

    @Override
    public Page<ADTConst> findADTConstPage(Specification<ADTConst> specification, Pageable pageable) {

        return adtConstRepository.findAll(specification, pageable);

    }

    @Override
    public ADTConst getADTConstValueByCode(ADTConstCode code) {

        ADTConst adtConst = adtConstRepository.findADTConstByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ADT_CONST_NOT_FOUND_CODE));

        return adtConst;

    }

}
