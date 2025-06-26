package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "reply")
@NoArgsConstructor
public class ReplyEntity extends BaseUuidEntity {

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private ReviewEntity reviewEntity;

    public ReplyEntity(
        String content,
        UUID userId,
        ReviewEntity reviewEntity
    ) {
        this.content = content;
        this.userId = userId;
        this.reviewEntity = reviewEntity;
    }

}
