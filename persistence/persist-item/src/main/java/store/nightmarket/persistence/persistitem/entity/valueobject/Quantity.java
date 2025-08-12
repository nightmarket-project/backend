package store.nightmarket.persistence.persistitem.entity.valueobject;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Quantity {

    private BigDecimal value;

}
