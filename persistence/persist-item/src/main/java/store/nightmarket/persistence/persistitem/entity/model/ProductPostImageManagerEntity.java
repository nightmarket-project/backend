package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.itemweb.state.ImageType;

@Getter
@Entity
@Table(name = "product_post_image_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostImageManagerEntity extends ImageManagerEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	private ProductPostImageManagerEntity(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ProductPostEntity productPostEntity
	) {
		super(
			id,
			displayOrder,
			type,
			imageEntity
		);
		this.productPostEntity = productPostEntity;
	}

	public static ProductPostImageManagerEntity newInstance(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity,
		ProductPostEntity productPostEntity
	) {
		return new ProductPostImageManagerEntity(
			id,
			displayOrder,
			type,
			imageEntity,
			productPostEntity
		);
	}

}
