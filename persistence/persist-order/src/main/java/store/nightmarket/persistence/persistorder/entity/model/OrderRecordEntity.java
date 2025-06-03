package store.nightmarket.persistence.persistorder.entity.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistorder.entity.valueobject.AddressEntity;

@Getter
@Entity
@Table(name = "order_record")
@NoArgsConstructor
@AllArgsConstructor
public class OrderRecordEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "address")
	private AddressEntity addressEntity;

	@Column(name = "order_date", nullable = false)
	private LocalDate orderDate;

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@OneToMany(mappedBy = "orderRecordEntity", fetch = FetchType.LAZY)
	private List<DetailOrderRecordEntity> detailOrderRecordList = new ArrayList<>();

}
