package store.nightmarket.itemweb.model;

import java.time.LocalDate;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReviewId;

@Getter
public class Review extends BaseModel<ReviewId> {

    private UserId author;
    private Content content;
    private Image image;
    private Rating rating;
    private final LocalDate createdAt;
    private boolean deleted;

    private Review(
        ReviewId id,
        UserId author,
        Content content,
        Image image,
        Rating rating
    ) {
        super(id);
        this.author = author;
        this.content = content;
        this.image = image;
        this.rating = rating;
        this.createdAt = LocalDate.now();
        deleted = false;
    }

    public static Review newInstance(
        ReviewId id,
        UserId author,
        Content content,
        Image image,
        Rating rating
    ) {
        return new Review(
            id,
            author,
            content,
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

        this.content = Content.deleted();
        deleted = true;
    }

}
