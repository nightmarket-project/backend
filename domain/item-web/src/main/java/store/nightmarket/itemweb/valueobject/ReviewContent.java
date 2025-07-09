package store.nightmarket.itemweb.valueobject;

import java.util.Objects;
import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class ReviewContent  {

    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private String description;
    private Rating rating;
    private Image image;

    public ReviewContent(
        String description,
        Rating rating,
        Image image
    ) {
        validte(description);
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    private void validte(String description) {
        if (description.isBlank()) {
            throw new ItemWebException("Text is blank");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
    }

    public static ReviewContent deleted() {
        return new ReviewContent(
            "삭제된 댓글입니다.",
            new Rating(0),
            null
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ReviewContent that = (ReviewContent) obj;
        return Objects.equals(description, that.description)
            && Objects.equals(rating, that.rating)
            && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, rating, image);
    }

}
