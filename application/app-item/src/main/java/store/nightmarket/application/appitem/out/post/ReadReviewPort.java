package store.nightmarket.application.appitem.out.post;

import java.util.List;

import store.nightmarket.application.appitem.out.post.mapper.dto.ReviewAdapterDto;
import store.nightmarket.itemweb.model.id.ProductPostId;

public interface ReadReviewPort {

	List<ReviewAdapterDto> read(ProductPostId productPostId);

}
