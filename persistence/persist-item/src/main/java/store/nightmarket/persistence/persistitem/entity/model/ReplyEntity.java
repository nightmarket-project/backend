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
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.itemweb.valueobject.Content;

@Getter
@Entity
@Table(name = "reply")
@NoArgsConstructor
public class ReplyEntity extends BaseUuidEntity {

    @Embedded
    @Column(name = "content", nullable = false)
    private Content content;

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewEntity reviewEntity;

    @Column(name = "create_date", nullable = false)
    private LocalDate createdAt;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public ReplyEntity(
        Content content,
        UUID userId,
        ReviewEntity reviewEntity,
        LocalDate createdAt,
        boolean deleted
    ) {
        this.content = content;
        this.userId = userId;
        this.reviewEntity = reviewEntity;
        this.createdAt = createdAt;
        this.deleted = deleted;
    }

}
