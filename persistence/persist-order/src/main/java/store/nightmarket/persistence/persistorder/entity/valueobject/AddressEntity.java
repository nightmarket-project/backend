package store.nightmarket.persistence.persistorder.entity.valueobject;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressEntity {

	private String zipCode;
	private String roadAddress;
	private String detailAddress;

}
