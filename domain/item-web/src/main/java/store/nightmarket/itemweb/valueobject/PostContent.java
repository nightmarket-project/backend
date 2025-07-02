package store.nightmarket.itemweb.valueobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import store.nightmarket.itemweb.exception.ItemWebException;

@Getter
public class PostContent {

    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    private String description;
    private Rating rating;
    private List<Image> imageList;

    public PostContent(
        String description,
        Rating rating,
        List<Image> imageList
    ) {
        validte(description);
        this.description = description;
        this.rating = rating;
        this.imageList = imageList;
    }

    private void validte(String description) {
        if (description.isBlank()) {
            throw new ItemWebException("Text is blank");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ItemWebException("Text is too long");
        }
    }


    public static PostContent deleted() {
        return new PostContent(
            "삭제된 게시글입니다.",
            new Rating(0),
            new ArrayList<>()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PostContent that = (PostContent) obj;
        return Objects.equals(description, that.description)
            && Objects.equals(rating, that.rating)
            && Objects.equals(imageList, that.imageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            description,
            rating,
            imageList
        );
    }

}
