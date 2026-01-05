package store.nightmarket.application.appitem.out.variant.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.variant.mapper.dto.ProductVariantAdapterDto;
import store.nightmarket.application.appitem.out.variant.mapper.ProductVariantMapper;
import store.nightmarket.application.appitem.out.variant.ReadProductVariantPort;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@Component
@RequiredArgsConstructor
public class ReadProductVariantAdapter implements ReadProductVariantPort {

	private final ProductVariantRepository productVariantRepository;

	@Override
	public Optional<ProductVariant> read(ProductVariantId id) {
		return productVariantRepository.findById(id.getId()).map(ProductVariantMapper::toDomain);
	}

	@Override
	public List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId productId) {
		return productVariantRepository.findByProductId(productId.getId()).stream()
			.map(ProductVariantAdapterDto::toDomain)
			.toList();
	}

	@Override
	public Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(
		List<ProductVariantId> productVariantIdList) {
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
