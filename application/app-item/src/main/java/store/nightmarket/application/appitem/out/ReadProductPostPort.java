package store.nightmarket.application.appitem.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;

public interface ReadProductPostPort {

	Page<ProductPostAdapterDto> findProductPostListByKeyword(String keyword, Pageable pageable);

}
