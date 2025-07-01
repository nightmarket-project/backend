package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.ReviewContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Review extends BaseModel<ReviewId> {

    private UserId author;
    private ReviewContent reviewContent;
    private final LocalDate createdAt;
    private boolean deleted;

    private Review(
        ReviewId id,
        UserId author,
        ReviewContent reviewContent
    ) {
        super(id);
        this.author = author;
        this.reviewContent = reviewContent;
        this.createdAt = LocalDate.now();
        deleted = false;
    }

    public static Review newInstance(
        ReviewId id,
        UserId author,
        ReviewContent reviewContent
    ) {
        return new Review(
            id,
            author,
            reviewContent
        );
    }

    public void delete(UserId currentUserId) {
        if (deleted) {
            throw new ItemWebException("이미 삭제된 댓글입니다.");
        }
        if (!currentUserId.equals(author)) {
            throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
        }

        this.reviewContent = ReviewContent.deleted();
        deleted = true;
    }

    public void edit(
        UserId authorId,
        ReviewContent editContent
    ) {
        if (!authorId.equals(this.author)) {
            throw new ItemWebException("댓글 작성자만 수정 가능합니다.");
        }
        this.reviewContent = editContent;
    }

}
