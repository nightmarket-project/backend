package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.CommentText;

@Getter
@Entity
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "content", nullable = false)
    private CommentText commentText;

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @Column(name = "create_date", nullable = false)
    private LocalDate createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity reviewEntity;

    public ReplyEntity(
        CommentText commentText,
        UUID userId,
        LocalDate createdAt,
        boolean deleted,
        ReviewEntity reviewEntity
    ) {
        this.commentText = commentText;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.reviewEntity = reviewEntity;
    }

    public static ReplyEntity newInstance(
        CommentText commentText,
        UUID userId,
        LocalDate createdAt,
        boolean deleted,
        ReviewEntity reviewEntity
    ) {
        return new ReplyEntity(
            commentText,
            userId,
            createdAt,
            deleted,
            reviewEntity
        );
    }

}
