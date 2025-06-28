package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ContentEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.RatingEntity;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor
public class ReviewEntity extends BaseUuidEntity {

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Embedded
    @Column(name = "content")
    private ContentEntity content;

    @Embedded
    @Column(name = "image")
    private ImageEntity image;

    @Embedded
    @Column(name = "rating", nullable = false)
    private RatingEntity ratingEntity;


    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_post_id")
    private ProductPostEntity productPostEntity;

    @OneToOne(mappedBy = "reviewEntity", fetch = FetchType.LAZY)
    private ReplyEntity replyEntity;

    public ReviewEntity(
        UUID userId,
        ContentEntity content,
        ImageEntity image,
        RatingEntity ratingEntity,
        LocalDate createDate,
        boolean deleted,
        ProductPostEntity productPostEntity,
        ReplyEntity replyEntity
    ) {
        this.userId = userId;
        this.content = content;
        this.image = image;
        this.ratingEntity = ratingEntity;
        this.createDate = createDate;
        this.deleted = deleted;
        this.productPostEntity = productPostEntity;
        this.replyEntity = replyEntity;
    }
}
