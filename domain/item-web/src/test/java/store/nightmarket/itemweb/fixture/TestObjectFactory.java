package store.nightmarket.itemweb.fixture;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReplyContent;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class TestObjectFactory {

    public static Review createReview(
        UUID reviewId,
        UUID authorId,
        ReviewContent reviewContent
    ) {
        return Review.newInstance(
            new ReviewId(reviewId),
            new UserId(authorId),
            reviewContent
        );
    }

    public static Reply createReply(
        UUID replyId,
        ReplyContent replyContent,
        UUID authorId,
        UUID reviewId
    ) {
        return Reply.newInstance(
            new ReplyId(replyId),
            replyContent,
            new UserId(authorId),
            new ReviewId(replyId)
        );
    }

    public static ReplyContent createReplyContent(String description) {
        return new ReplyContent(description);
    }

    public static ReviewContent createReviewContent(
        String description,
        int rating,
        Image image
    ) {
        return new ReviewContent(
            description,
            new Rating(rating),
            image
        );
    }

    public static PostContent createPostContent(
        String description,
        int rating,
        List<Image> imageList
    ) {
        return new PostContent(
            description,
            new Rating(rating),
            imageList
        );
    }

    public static Image createImage(
        String url,
        String altText,
        int sortNum
    ) {
        return new Image(
            url,
            altText,
            sortNum
        );
    }

    public static Image defaultImage() {
        Random random = new Random();
        return new Image(
            "https://picsum.photos/" + random.nextInt() + "/" + random.nextInt(),
            "배경사진",
            1
        );
    }

}
