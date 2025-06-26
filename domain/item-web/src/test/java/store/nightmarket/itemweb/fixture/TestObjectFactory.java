package store.nightmarket.itemweb.fixture;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

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

    public static Image defaultImage() {
        Random random = new Random();
        return new Image(
                "https://picsum.photos/" + random.nextInt() + "/" + random.nextInt(),
                random.nextInt(100)
        );
    }

}
