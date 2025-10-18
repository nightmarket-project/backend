package store.nightmarket.application.appitem.out.adaptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ProductVariantMapper;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadProductVariantAdaptor implements ReadProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId productId) {
		return productVariantRepository.findByProductId(productId.getId()).stream()
			.map(ProductVariantAdapterDto::toDomain)
			.toList();
	}

	@Override
	public Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(List<ProductVariantId> productVariantIdList) {
		Map<ProductVariantId, ProductPostId> variantIdProductPostIdMap = new HashMap<>();

		productVariantRepository.findProductPostIdsByProductVariantIds(
				productVariantIdList.stream()
					.map(ProductVariantId::getId)
					.toList())
			.forEach(productVariantPostIdSummary ->
				variantIdProductPostIdMap.put(
					new ProductVariantId(productVariantPostIdSummary.productVariantId()),
					new ProductPostId(productVariantPostIdSummary.productPostId())
				));

		return variantIdProductPostIdMap;
	}

	@Override
	public List<ProductVariant> readByIdList(List<ProductVariantId> productVariantId) {
		List<UUID> uuidList = productVariantId.stream()
			.map(ProductVariantId::getId)
			.toList();
		
		return productVariantRepository.findByIdList(uuidList)
			.stream()
			.map(ProductVariantMapper::toDomain)
			.toList();
	}

}
