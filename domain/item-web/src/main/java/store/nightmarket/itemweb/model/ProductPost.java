package store.nightmarket.itemweb.model;

import java.util.ArrayList;
import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.itemweb.valueobject.Rating;

public class ProductPost extends BaseModel<ProductPostId> {

    private final ProductId productId;
    private List<Image> imageList;
    private List<Review> reviewList;
    private Rating rating;
    private boolean deleted;

    private ProductPost(
        ProductPostId id,
        ProductId productId,
        List<Image> imageList,
        List<Review> reviewList
    ) {
        super(id);
        this.productId = productId;
        this.imageList = new ArrayList<>(imageList);
        this.reviewList = new ArrayList<>(reviewList);
        this.rating = caculateRating();
        this.deleted = false;
    }

    public static ProductPost newInstance(
        ProductPostId id,
        ProductId productId,
        List<Image> imageList,
        List<Review> reviewList
    ) {
        return new ProductPost(
            id,
            productId,
            imageList,
            reviewList
        );
    }

    private Rating caculateRating() {
        if (reviewList.isEmpty()) {
            return new Rating(0);
        }

        double result = reviewList.stream()
            .filter(review -> review.getRating().value() != 0)
            .mapToDouble(review -> review.getRating().value())
            .sum() / reviewList.size();

        return new Rating((float) (Math.round(result * 10.0) / 10.0));
    }

}
