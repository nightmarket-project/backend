package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor
public class ReviewEntity extends BaseUuidEntity {

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Embedded
    @Column(name = "rating", nullable = false)
    private RatingEntity ratingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPostEntity productPostEntity;

    @OneToOne(mappedBy = "reviewEntity", fetch = FetchType.LAZY)
    private ReplyEntity replyEntity;

    public ReviewEntity(
        UUID userId,
        RatingEntity ratingEntity,
        ProductPostEntity productPostEntity,
        ReplyEntity replyEntity
    ) {
        this.userId = userId;
        this.ratingEntity = ratingEntity;
        this.productPostEntity = productPostEntity;
        this.replyEntity = replyEntity;
    }

}
