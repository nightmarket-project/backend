package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.Name;
import store.nightmarket.persistence.persistitem.entity.valueobject.Quantity;

@Getter
@Entity
@Table(name = "shopping_basket_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingBasketProductEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private Name name;

    @Embedded
    @Column(name = "quantity")
    private Quantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant")
    private ProductVariantEntity productVariantEntity;

    //추후 UserEntity 매핑 예정

    private ShoppingBasketProductEntity(
        Name name,
        Quantity quantity,
        ProductVariantEntity productVariantEntity
    ) {
        this.name = name;
        this.quantity = quantity;
        this.productVariantEntity = productVariantEntity;
    }

    public static ShoppingBasketProductEntity newInstance(
        Name name,
        Quantity quantity,
        ProductVariantEntity productVariantEntity
    ) {
        return new ShoppingBasketProductEntity(
            name,
            quantity,
            productVariantEntity
        );
    }

}
