package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "cart_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private NameEntity nameEntity;

    @Embedded
    @Column(name = "quantity")
    private QuantityEntity quantityEntity;

    @OneToMany(mappedBy = "cartProductEntity", fetch = FetchType.LAZY)
    private List<CartProductVariantEntity> cartProductVariantEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    private CartProductEntity(
        NameEntity nameEntity,
        QuantityEntity quantityEntity,
        List<CartProductVariantEntity> cartProductVariantEntityList
    ) {
        this.nameEntity = nameEntity;
        this.quantityEntity = quantityEntity;
        this.cartProductVariantEntityList = cartProductVariantEntityList;
    }

    public static CartProductEntity newInstance(
        NameEntity nameEntity,
        QuantityEntity quantityEntity,
        List<CartProductVariantEntity> cartProductVariantEntityList
    ) {
        return new CartProductEntity(
            nameEntity,
            quantityEntity,
            cartProductVariantEntityList
        );
    }


}
