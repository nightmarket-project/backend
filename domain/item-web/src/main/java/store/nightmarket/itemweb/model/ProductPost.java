package store.nightmarket.itemweb.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public class ProductPost extends BaseModel<ProductPostId> {

    private ProductId productId;
    private PostContent postContent;
    private List<Review> review;
    private boolean deleted;

    private ProductPost(
        ProductPostId id,
        ProductId productId,
        PostContent postContent,
        List<Review> review
    ) {
        super(id);
        this.productId = productId;
        this.postContent = postContent;
        this.review = review;
        this.deleted = false;
    }

    public static ProductPost newInstance(
        ProductPostId id,
        ProductId productId,
        PostContent postContent,
        List<Review> review
    ) {
        return new ProductPost(
            id,
            productId,
            postContent,
            review
        );
    }

}
