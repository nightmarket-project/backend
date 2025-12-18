package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.itemweb.model.state.ImageType;

class ReadShoppingBasketUseCaseTest {

	private ReadShoppingBasketProductPort mockReadShoppingBasketProductPort;
	private ReadProductVariantPort mockReadProductVariantPort;
	private ReadImageManagerPort mockReadImageManagerPort;
	private ReadShoppingBasketUseCase readShoppingBasketUseCase;

	@BeforeEach
	void setUp() {
		mockReadShoppingBasketProductPort = mock(ReadShoppingBasketProductPort.class);
		mockReadProductVariantPort = mock(ReadProductVariantPort.class);
		mockReadImageManagerPort = mock(ReadImageManagerPort.class);
		readShoppingBasketUseCase = new ReadShoppingBasketUseCase(
			mockReadShoppingBasketProductPort,
			mockReadProductVariantPort,
			mockReadImageManagerPort
		);
	}

	@Test
	@DisplayName("장바구니 목록 조회")
	void readShoppingBasket() {
		// given
		UserId userId = new UserId(UUID.randomUUID());
		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

		ReadShoppingBasketUseCaseDto.Input input = ReadShoppingBasketUseCaseDto.Input.builder()
			.userId(userId)
			.build();

		List<ShoppingBasketProduct> shoppingBasketProductList = List.of(
			TestDomainFactory.createShoppingBasketProduct(
				UUID.randomUUID(),
				userId.getId(),
				productVariantId1.getId(),
				BigInteger.TEN
			),
			TestDomainFactory.createShoppingBasketProduct(
				UUID.randomUUID(),
				userId.getId(),
				productVariantId2.getId(),
				BigInteger.ONE
			)
		);
		Map<ProductVariantId, ProductPostId> variantIdProductPostIdMap = new HashMap<>();
		variantIdProductPostIdMap.put(productVariantId1, productPostId);
		variantIdProductPostIdMap.put(productVariantId2, productPostId);
		ImageManager imageManager = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			ImageType.THUMBNAIL,
			1,
			productPostId.getId()
		);

		when(mockReadShoppingBasketProductPort.readListByUserId(userId))
			.thenReturn(shoppingBasketProductList);
		when(mockReadProductVariantPort.findProductPostIdsByVariantIds(List.of(productVariantId1, productVariantId2)))
			.thenReturn(variantIdProductPostIdMap);
		when(mockReadImageManagerPort.readThumbnailList(List.of(productPostId, productPostId)))
			.thenReturn(List.of(imageManager));

		// when
		ReadShoppingBasketUseCaseDto.Output output = readShoppingBasketUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.shoppingBasketProductList())
			.isEqualTo(shoppingBasketProductList);
		assertThat(output.variantIdProductPostIdMap())
			.isEqualTo(variantIdProductPostIdMap);
		assertThat(output.imageManagerList())
			.isEqualTo(List.of(imageManager));
		verify(mockReadShoppingBasketProductPort, times(1))
			.readListByUserId(userId);
		verify(mockReadProductVariantPort, times(1))
			.findProductPostIdsByVariantIds(List.of(productVariantId1, productVariantId2));
		verify(mockReadImageManagerPort, times(1))
			.readThumbnailList(List.of(productPostId, productPostId));
	}

}
