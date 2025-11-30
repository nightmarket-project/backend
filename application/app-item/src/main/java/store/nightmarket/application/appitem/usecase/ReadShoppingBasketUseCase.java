package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.id.ProductPostId;

@Service
@RequiredArgsConstructor
public class ReadShoppingBasketUseCase implements BaseUseCase<Input, Output> {

	private final ReadShoppingBasketProductPort readShoppingBasketProductPort;
	private final ReadProductVariantPort readProductVariantPort;
	private final ReadImageManagerPort readImageManagerPort;

	@Override
	public Output execute(Input input) {
		List<ShoppingBasketProduct> shoppingBasketProductlist = readShoppingBasketProductPort.readListByUserId(input.userId());
		List<ProductVariantId> productVariantIdList = shoppingBasketProductlist.stream()
			.map(ShoppingBasketProduct::getVariantId)
			.toList();
		Map<ProductVariantId, ProductPostId> variantPostIdMap = readProductVariantPort.findProductPostIdsByVariantIds(productVariantIdList);

		List<ImageManager> imageManagerList = readImageManagerPort.readThumbnailList(new ArrayList<>(variantPostIdMap.values()));

		return Output.builder()
			.shoppingBasketProductList(shoppingBasketProductlist)
			.imageManagerList(imageManagerList)
			.variantIdProductPostIdMap(variantPostIdMap)
			.build();
	}

}
