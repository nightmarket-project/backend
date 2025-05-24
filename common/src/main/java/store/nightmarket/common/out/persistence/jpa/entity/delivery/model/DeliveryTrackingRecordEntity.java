package store.nightmarket.common.out.persistence.jpa.entity.delivery.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.out.persistence.jpa.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.common.out.persistence.jpa.entity.delivery.valueobject.DetailDeliveryStateEntity;
import store.nightmarket.common.out.persistence.jpa.entity.delivery.valueobject.LocationEntity;

@Getter
@Entity
@Table(name = "delivery_traking_record")
@NoArgsConstructor
public class DeliveryTrackingRecordEntity extends BaseAutoIncrementIdEntity {

	@Column(name = "time", nullable = false)
	private LocalDateTime time;

	@Embedded
	private LocationEntity locationEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private DetailDeliveryStateEntity state;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_record_id", nullable = false)
	private DeliveryRecordEntity deliveryRecordEntity;

	public DeliveryTrackingRecordEntity(
		LocalDateTime time,
		LocationEntity locationEntity,
		DetailDeliveryStateEntity state,
		String content
	) {
		this.time = time;
		this.locationEntity = locationEntity;
		this.state = state;
		this.content = content;
	}

}
