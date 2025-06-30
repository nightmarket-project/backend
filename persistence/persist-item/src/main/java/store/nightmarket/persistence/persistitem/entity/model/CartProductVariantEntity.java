package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;

@Getter
@Entity
@Table(name = "cart_product_variant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductVariantEntity extends BaseAutoIncrementIdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariantEntity productVariantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_product_id")
    private CartProductEntity cartProductEntity;

}
