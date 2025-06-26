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
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "cart_product")
@NoArgsConstructor
public class CartProductEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private NameEntity name;

    @Embedded
    @Column(name = "quantity")
    private QuantityEntity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;

    @OneToMany(mappedBy = "cartProduct", fetch = FetchType.LAZY)
    private List<ProductVariantCartProductRelationEntity> productVariantCartProductRelationEntityList
        = new ArrayList<>();

    public CartProductEntity(
        CartEntity cartEntity,
        QuantityEntity quantity,
        NameEntity name
    ) {
        this.cartEntity = cartEntity;
        this.quantity = quantity;
        this.name = name;
    }

}
