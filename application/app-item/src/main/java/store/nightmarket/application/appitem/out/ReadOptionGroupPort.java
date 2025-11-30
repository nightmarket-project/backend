package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.domain.item.model.id.ProductId;

public interface ReadOptionGroupPort {

	List<OptionGroupAdapterDto> readFetchOptionValue(ProductId id);

}
