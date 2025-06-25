package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.common.entity.BaseEntity;

@Getter
@Entity
@Table(name = "prduct_variant_cart_product_relation")
@NoArgsConstructor
public class ProductVariantCartProductRelationEntity extends BaseAutoIncrementIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariantEntity productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_product_id")
    private CartProductEntity cartProduct;

}
