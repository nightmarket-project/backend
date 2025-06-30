package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private NameEntity nameEntity;

    @Column(name = "description")
    private String description;

    @Embedded
    @Column(name = "price")
    private PriceEntity price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPostEntity productPostEntity;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<OptionGroupEntity> optionGroupEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<ProductVariantEntity> productVariantEntityList = new ArrayList<>();

    private ProductEntity(
        NameEntity nameEntity,
        String description,
        PriceEntity priceEntity,
        ProductPostEntity productPostEntity,
        List<OptionGroupEntity> optionGroupEntityList,
        List<ProductVariantEntity> productVariantEntityList
    ) {
        this.nameEntity = nameEntity;
        this.description = description;
        this.price = priceEntity;
        this.productPostEntity = productPostEntity;
        this.optionGroupEntityList = optionGroupEntityList;
        this.productVariantEntityList = productVariantEntityList;
    }

    public static ProductEntity newInstance(
        NameEntity nameEntity,
        String description,
        PriceEntity priceEntity,
        ProductPostEntity productPostEntity,
        List<OptionGroupEntity> optionGroupEntityList,
        List<ProductVariantEntity> productVariantEntityList
    ) {
        return new ProductEntity(nameEntity,
            description,
            priceEntity,
            productPostEntity,
            optionGroupEntityList,
            productVariantEntityList
        );
    }

}
