package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.application.appitem.out.dto.ReviewAdapterDto;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadReviewPort {

	List<ReviewAdapterDto> read(ProductPostId productPostId);

}
