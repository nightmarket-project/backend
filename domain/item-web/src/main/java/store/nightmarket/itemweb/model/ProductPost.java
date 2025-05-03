package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ProductPostId;

import java.util.List;

public class ProductPost extends BaseModel<ProductPostId> {

    private ItemId itemId;
    private PostContent content;
    private List<Review> review;

    private ProductPost(
            ProductPostId id,
            ItemId itemId,
            PostContent content,
            List<Review> review
    ) {
        super(id);
        this.itemId = itemId;
        this.content = content;
        this.review = review;
    }

    public static ProductPost newInstance(
            ProductPostId id,
            ItemId itemId,
            PostContent content,
            List<Review> review
    ) {
        return new ProductPost(
                id,
                itemId,
                content,
                review
        );
    }
}
