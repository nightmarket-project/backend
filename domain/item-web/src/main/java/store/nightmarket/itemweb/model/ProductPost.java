package store.nightmarket.itemweb.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemGroupId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ProductPostId;

import java.util.List;

public class ProductPost extends BaseModel<ProductPostId> {

    private ItemGroupId itemGroupId;
    private PostContent content;
    private List<Review> review;

    private ProductPost(
            ProductPostId id,
            ItemGroupId itemGroupId,
            PostContent content,
            List<Review> review
    ) {
        super(id);
        this.itemGroupId = itemGroupId;
        this.content = content;
        this.review = review;
    }

    public static ProductPost newInstance(
            ProductPostId id,
            ItemGroupId itemGroupId,
            PostContent content,
            List<Review> review
    ) {
        return new ProductPost(
                id,
            itemGroupId,
                content,
                review
        );
    }

}
