package store.nightmarket.itemweb.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.Content;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public class ProductPost extends BaseModel<ProductPostId> {

    private ProductId productId;
    private Content content;
    private List<Review> review;

    private ProductPost(
        ProductPostId id,
        ProductId productId,
        Content content,
        List<Review> review
    ) {
        super(id);
        this.productId = productId;
        this.content = content;
        this.review = review;
    }

    public static ProductPost newInstance(
        ProductPostId id,
        ProductId productId,
        Content content,
        List<Review> review
    ) {
        return new ProductPost(
            id,
            productId,
            content,
            review
        );
    }

}
