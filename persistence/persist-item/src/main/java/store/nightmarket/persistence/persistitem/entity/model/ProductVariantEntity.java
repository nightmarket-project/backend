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
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "product_variant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductVariantEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "quantity")
    private QuantityEntity quantityEntity;

    @OneToMany(mappedBy = "productVariantEntity", fetch = FetchType.LAZY)
    private List<VariantOptionValueEntity> variantOptionValueEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    private ProductVariantEntity(
        QuantityEntity quantityEntity,
        ProductEntity productEntity,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        this.quantityEntity = quantityEntity;
        this.productEntity = productEntity;
        this.variantOptionValueEntityList = variantOptionValueEntityList;
    }

    public static ProductVariantEntity newInstance(
        QuantityEntity quantityEntity,
        ProductEntity productEntity,
        List<VariantOptionValueEntity> variantOptionValueEntityList
    ) {
        return new ProductVariantEntity(
            quantityEntity,
            productEntity,
            variantOptionValueEntityList
        );
    }

}
