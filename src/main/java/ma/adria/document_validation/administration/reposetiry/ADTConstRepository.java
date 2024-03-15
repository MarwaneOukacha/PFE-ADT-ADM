package ma.adria.document_validation.administration.reposetiry;

import ma.adria.document_validation.administration.model.entities.ADTConst;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ADTConstRepository extends PagingAndSortingRepository<ADTConst, UUID>, JpaSpecificationExecutor<ADTConst> {

    boolean existsByCode(ADTConstCode code);

    Optional<ADTConst> findADTConstByCode(ADTConstCode code);

}