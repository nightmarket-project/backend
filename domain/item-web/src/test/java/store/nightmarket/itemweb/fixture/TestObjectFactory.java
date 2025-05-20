package store.nightmarket.itemweb.fixture;

import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.model.Review;
import store.nightmarket.itemweb.valueobject.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestObjectFactory {

    public static Reply createReply(String content) {
        return Reply.newInstance(
                new ReviewId(UUID.randomUUID()),
                content,
                new UserId(UUID.randomUUID())
        );
    }

    public static PostContent createPostContent(String test, List<Image> images) {
        return new PostContent(test, images);
    }

    public static Image createImage(String url, int order) {
        return new Image(url, order);
    }


    public static Reply defaultReply() {
        return Reply.newInstance(
                new ReviewId(UUID.randomUUID()),
                "hello",
                new UserId(UUID.randomUUID())
        );
    }

    public static Image defaultImage() {
        Random random = new Random();
        return new Image(
                "https://picsum.photos/" + random.nextInt() + "/" + random.nextInt(),
                random.nextInt(100)
        );
    }

}
