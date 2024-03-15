package ma.adria.document_validation.administration.model.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.adria.document_validation.administration.model.enums.ADTConstCode;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "adt_consts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ADTConst extends AbstractEntity {

    @Column(unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ADTConstCode code;

    @Column(nullable = false)
    private String value;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ADTConst adtConst = (ADTConst) o;

        return Objects.equals(id, adtConst.id);
    }



}