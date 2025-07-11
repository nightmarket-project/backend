package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Review extends BaseModel<ReviewId> {

    private final UserId author;
    private CommentText commentText;
    private Image image;
    private Rating rating;
    private final LocalDate createdAt;
    private boolean deleted;

    private Review(
        ReviewId id,
        UserId author,
        CommentText commentText,
        Image image,
        Rating rating
    ) {
        super(id);
        this.author = author;
        this.commentText = commentText;
        this.image = image;
        this.rating = rating;
        this.createdAt = LocalDate.now();
        deleted = false;
    }

    public static Review newInstance(
        ReviewId id,
        UserId author,
        CommentText commentText,
        Image image,
        Rating rating
    ) {
        return new Review(
            id,
            author,
            commentText,
            image,
            rating
        );
    }

    public void delete(UserId currentUserId) {
        if (deleted) {
            throw new ItemWebException("이미 삭제된 댓글입니다.");
        }
        if (!currentUserId.equals(author)) {
            throw new ItemWebException("댓글 작성자만 삭제 가능합니다.");
        }

        this.commentText = CommentText.deleted();
        deleted = true;
    }

    public void edit(
        UserId authorId,
        CommentText editContent,
        Rating editRating,
        Image editImage
    ) {
        if (!authorId.equals(this.author)) {
            throw new ItemWebException("댓글 작성자만 수정 가능합니다.");
        }

        this.commentText = (editContent == null) ? commentText : editContent;
        this.rating = (editRating == null) ? rating : editRating;
        this.image = (editImage == null) ? image : editImage;
    }

}
