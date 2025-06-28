package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ContentEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

@Getter
@Entity
@Table(name = "product_post")
@NoArgsConstructor
public class ProductPostEntity extends BaseUuidEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Embedded
    @Column(name = "post_content")
    private ContentEntity postContent;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ImageEntity> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "productPostEntity", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    public ProductPostEntity(
        ProductEntity productEntity,
        ContentEntity postContent,
        List<ImageEntity> imageList,
        List<ReviewEntity> reviewEntityList
    ) {
        this.productEntity = productEntity;
        this.postContent = postContent;
        this.imageList = imageList;
        this.reviewEntityList = reviewEntityList;
    }

}
