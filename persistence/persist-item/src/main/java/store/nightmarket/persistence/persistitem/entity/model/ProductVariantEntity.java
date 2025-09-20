package store.nightmarket.persistence.persistitem.entity.model;

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
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "product_variant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductVariantEntity extends BaseUuidEntity {

	@Column(name = "product_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID productId;

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@Column(name = "SKU_code", nullable = false)
	private String SKUCode;

	@Embedded
	@Column(name = "quantity")
	private QuantityEntity quantityEntity;

	@OneToMany(mappedBy = "productVariantEntity", fetch = FetchType.LAZY)
	private List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

	private ProductVariantEntity(
		UUID id,
		UUID productId,
		UUID userId,
		String SKUCode,
		QuantityEntity quantityEntity
	) {
		super(id);
		this.productId = productId;
		this.userId = userId;
		this.SKUCode = SKUCode;
		this.quantityEntity = quantityEntity;
	}

	public static ProductVariantEntity newInstance(
		UUID id,
		UUID productId,
		UUID userId,
		String SKUCode,
		QuantityEntity quantityEntity
	) {
		return new ProductVariantEntity(
			id,
			productId,
			userId,
			SKUCode,
			quantityEntity
		);
	}

}
