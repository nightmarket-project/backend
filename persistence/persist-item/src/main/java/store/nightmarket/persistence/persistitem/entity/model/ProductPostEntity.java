package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.CascadeType;
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
import store.nightmarket.persistence.persistitem.entity.valueobject.ContentEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

@Getter
@Entity
@Table(name = "product_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPostEntity extends BaseUuidEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Embedded
    @Column(name = "content")
    private ContentEntity contentEntity;

    @OneToMany(mappedBy = "productPostEntity", cascade = CascadeType.ALL,
        fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ImageEntity> imageEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "productPostEntity", cascade = CascadeType.ALL,
        fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    private ProductPostEntity(
        ProductEntity productEntity,
        ContentEntity contentEntity,
        List<ImageEntity> imageEntityList,
        List<ReviewEntity> reviewEntityList
    ) {
        this.productEntity = productEntity;
        this.contentEntity = contentEntity;
        this.imageEntityList = imageEntityList;
        this.reviewEntityList = reviewEntityList;
    }

    public static ProductPostEntity newInstance(
        ProductEntity productEntity,
        ContentEntity postContent,
        List<ImageEntity> imageEntityList,
        List<ReviewEntity> reviewEntityList
    ) {
        return new ProductPostEntity(
            productEntity,
            postContent,
            imageEntityList,
            reviewEntityList
        );
    }

}
