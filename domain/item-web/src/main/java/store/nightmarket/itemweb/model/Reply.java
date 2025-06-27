package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Reply extends BaseModel<ReplyId> {

    private Content content;
    private UserId author;
    private ReviewId reviewId;
    private final LocalDate createdAt;
    private boolean deleted;

    private Reply(
        ReplyId id,
        Content content,
        UserId author,
        ReviewId reviewId
    ) {
        super(id);
        this.content = content;
        this.author = author;
        this.reviewId = reviewId;
        createdAt = LocalDate.now();
        deleted = false;
    }

    public static Reply newInstance(
        ReplyId id,
        Content content,
        UserId author,
        ReviewId reviewId
    ) {
        return new Reply(
            id,
            content,
            author,
            reviewId
        );
    }


    public void delete(UserId currentUserId) {
        if (deleted) {
            throw new ItemWebException("이미 삭제된 댓글입니다.");
        }
        if (!currentUserId.equals(author)) {
            throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
        }

        this.content = Content.deleted();
        deleted = true;
    }


    public void edit(
        UserId userId,
        Content content
    ) {
        if(!userId.equals(author)) {
            throw new ItemWebException("댓글 작성자만 수정 가능합니다.");
        }
        this.content = content;
    }

}
