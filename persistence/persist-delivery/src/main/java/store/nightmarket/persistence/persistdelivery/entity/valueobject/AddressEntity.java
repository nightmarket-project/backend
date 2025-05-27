package store.nightmarket.persistence.persistdelivery.entity.valueobject;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

	private String zipCode;
	private String roadAddress;
	private String detailAddress;

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		AddressEntity that = (AddressEntity)o;
		return Objects.equals(zipCode, that.zipCode) && Objects.equals(roadAddress, that.roadAddress)
			&& Objects.equals(detailAddress, that.detailAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(zipCode, roadAddress, detailAddress);
	}

}
