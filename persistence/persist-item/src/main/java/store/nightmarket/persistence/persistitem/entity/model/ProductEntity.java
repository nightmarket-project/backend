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
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.Name;
import store.nightmarket.persistence.persistitem.entity.valueobject.Price;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "name")
    private Name name;

    @Column(name = "description")
    private String description;

    @Embedded
    @Column(name = "price")
    private Price price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPostEntity productPostEntity;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<OptionGroupEntity> optionGroupEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<ProductVariantEntity> productVariantEntityList = new ArrayList<>();

    private ProductEntity(
        UUID id,
        Name name,
        String description,
        Price price,
        ProductPostEntity productPostEntity
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.productPostEntity = productPostEntity;
    }

    public static ProductEntity newInstance(
        UUID id,
        Name name,
        String description,
        Price price,
        ProductPostEntity productPostEntity
    ) {
        return new ProductEntity(
            id,
            name,
            description,
            price,
            productPostEntity
        );
    }

}
