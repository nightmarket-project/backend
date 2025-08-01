package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private PriceEntity priceEntity;

    @OneToOne(mappedBy = "productEntity")
    private ProductPostEntity productPostEntity;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<OptionGroupEntity> optionGroupEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<ProductVariantEntity> productVariantEntityList = new ArrayList<>();

    private ProductEntity(
        UUID id,
        NameEntity nameEntity,
        String description,
        PriceEntity priceEntity,
        ProductPostEntity productPostEntity
    ) {
        super(id);
        this.nameEntity = nameEntity;
        this.description = description;
        this.priceEntity = priceEntity;
        this.productPostEntity = productPostEntity;
    }

    public static ProductEntity newInstance(
        UUID id,
        NameEntity nameEntity,
        String description,
        PriceEntity priceEntity,
        ProductPostEntity productPostEntity
    ) {
        return new ProductEntity(
            id,
            nameEntity,
            description,
            priceEntity,
            productPostEntity
        );
    }

}
