package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.application.appitem.out.mapper.dto.ReviewAdapterDto;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public interface ReadReviewPort {

	List<ReviewAdapterDto> read(ProductPostId productPostId);

}
