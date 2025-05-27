package store.nightmarket.persistence.persistdelivery.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.persistence.persistdelivery.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.persistence.persistdelivery.entity.valueobject.AddressEntity;

@Getter
@Entity
@Table(name = "delivery_record")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordEntity extends BaseAutoIncrementIdEntity {

	@Embedded
	private AddressEntity addressEntity;

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@OneToMany(mappedBy = "deliveryRecordEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DeliveryTrackingRecordEntity> deliveryTrackingRecords = new ArrayList<>();

}
