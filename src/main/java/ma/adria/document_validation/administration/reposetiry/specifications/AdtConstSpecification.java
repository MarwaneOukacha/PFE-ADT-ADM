package ma.adria.document_validation.administration.reposetiry.specifications;

import ma.adria.document_validation.administration.model.entities.ADTConst;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AdtConstSpecification {

    public Specification<ADTConst> codeLike(String code) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("code").as(String.class), "%" + code + "%");
    }

    public Specification<ADTConst> valueLike(String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("value"), "%" + value + "%");
    }

    public Specification<ADTConst> isEncrypted(boolean encrypted) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("encrypted"), encrypted);
    }

}
