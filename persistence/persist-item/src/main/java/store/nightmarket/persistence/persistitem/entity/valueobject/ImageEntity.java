package store.nightmarket.persistence.persistitem.entity.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;
import store.nightmarket.persistence.persistitem.entity.model.ReviewEntity;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends BaseAutoIncrementIdEntity {

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "order", nullable = false)
    private int order;

    @OneToOne(mappedBy = "imageEntity", fetch = FetchType.LAZY)
    private ReviewEntity reviewEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post")
    private ProductPostEntity productPostEntity;

    private ImageEntity(
        String url,
        int order
    ) {
        this.url = url;
        this.order = order;
    }

    public static ImageEntity newInstance(
        String url,
        int order
    ) {
        return new ImageEntity(
            url,
            order
        );
    }

}
