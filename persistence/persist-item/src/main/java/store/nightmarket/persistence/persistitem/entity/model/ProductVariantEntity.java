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
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "product_variant")
@NoArgsConstructor
public class ProductVariantEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "quantity")
    private QuantityEntity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @OneToMany(mappedBy = "productVariantEntity", fetch = FetchType.LAZY)
    private List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    public ProductVariantEntity(
        QuantityEntity quantity,
        ProductEntity productEntity,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        this.quantity = quantity;
        this.productEntity = productEntity;
        this.variantOptionValueEntityList = variantOptionValueEntityList;
    }

}
