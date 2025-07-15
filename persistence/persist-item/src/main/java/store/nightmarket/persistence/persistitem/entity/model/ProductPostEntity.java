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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.Rating;

@Getter
@Entity
@Table(name = "product_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostEntity extends BaseUuidEntity {

    @OneToOne(mappedBy = "productPostEntity")
    private ProductEntity productEntity;

    @Embedded
    private Rating rating;

    @OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
    private List<ImageEntity> imageEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    private ProductPostEntity(
        ProductEntity productEntity,
        Rating rating,
        List<ImageEntity> imageEntityList,
        List<ReviewEntity> reviewEntityList,
        boolean deleted
    ) {
        this.productEntity = productEntity;
        this.rating = rating;
        this.imageEntityList = imageEntityList;
        this.reviewEntityList = reviewEntityList;
        this.deleted = deleted;
    }

    public static ProductPostEntity newInstance(
        ProductEntity productEntity,
        Rating rating,
        List<ImageEntity> imageEntityList,
        List<ReviewEntity> reviewEntityList,
        boolean deleted
    ) {
        return new ProductPostEntity(
            productEntity,
            rating,
            imageEntityList,
            reviewEntityList,
            deleted
        );
    }

}
