package store.nightmarket.itemweb.fixture;

import java.util.Random;
import java.util.UUID;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.Rating;
import store.nightmarket.itemweb.valueobject.ReplyId;
import store.nightmarket.itemweb.valueobject.ReviewId;

public class TestObjectFactory {

    public static Review createReview(
        UUID reviewId,
        UUID authorId,
        String content,
        Image image,
        int rating
    ) {
        return Review.newInstance(
            new ReviewId(reviewId),
            new UserId(authorId),
            new Content(content),
            image,
            new Rating(rating)
        );
    }

    public static Reply createReply(String content) {
        return Reply.newInstance(
            new ReplyId(UUID.randomUUID()),
            new Content(content),
            new UserId(UUID.randomUUID()),
            new ReviewId(UUID.randomUUID())
        );
    }

    public static Reply createReply(
        UUID replyId,
        String content,
        UUID userId,
        UUID reviewId
    ) {
        return Reply.newInstance(
            new ReplyId(replyId),
            new Content(content),
            new UserId(userId),
            new ReviewId(reviewId)
        );
    }

    public static Content createContent(String test) {
        return new Content(test);
    }

    public static Image createImage(
        String url
    ) {
        return new Image(url);
    }

    public static Image defaultImage() {
        Random random = new Random();
        return new Image("https://picsum.photos/" + random.nextInt() + "/" + random.nextInt());
    }

}
