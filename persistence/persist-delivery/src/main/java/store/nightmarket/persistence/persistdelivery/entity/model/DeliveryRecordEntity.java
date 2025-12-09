package store.nightmarket.persistence.persistdelivery.entity.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistdelivery.entity.valueobject.AddressEntity;

@Getter
@Entity
@Table(name = "delivery_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRecordEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "address", nullable = false)
	private AddressEntity addressEntity;

	@Column(name = "user_id", nullable = false)
	private UUID userId;

	@OneToMany(mappedBy = "deliveryRecordEntity", fetch = FetchType.LAZY)
	private List<DeliveryTrackingRecordEntity> deliveryTrackingRecords = new ArrayList<>();

	public DeliveryRecordEntity(
		UUID id,
		AddressEntity addressEntity,
		UUID userId,
		List<DeliveryTrackingRecordEntity> deliveryTrackingRecords
	) {
		super(id);
		this.addressEntity = addressEntity;
		this.userId = userId;
		this.deliveryTrackingRecords = deliveryTrackingRecords;
	}

	public DeliveryRecordEntity(
		UUID id,
		LocalDateTime createdAt,
		AddressEntity addressEntity,
		UUID userId,
		List<DeliveryTrackingRecordEntity> deliveryTrackingRecords
	) {
		super(id, createdAt);
		this.addressEntity = addressEntity;
		this.userId = userId;
		this.deliveryTrackingRecords = deliveryTrackingRecords;
	}

}
