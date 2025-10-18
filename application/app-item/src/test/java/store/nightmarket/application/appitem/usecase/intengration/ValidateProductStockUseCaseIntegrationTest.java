package store.nightmarket.application.appitem.usecase.intengration;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.usecase.ValidateProductStockUseCase;
import store.nightmarket.application.appitem.usecase.dto.ValidateProductStockUseCaseDto;
import store.nightmarket.domain.item.exception.QuantityException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.exception.ItemWebException;

@SpringBootTest
public class ValidateProductStockUseCaseIntegrationTest {

	@Autowired
	private ValidateProductStockUseCase validateProductStockUseCase;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@MockitoBean
	private ReadProductVariantPort readProductVariantPort;

	private static final String STOCK_CACHE_KEY_PREFIX = "stock:";

	@BeforeEach
	void setUp() {
		reset(readProductVariantPort);
		Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
	}

	@Test
	@DisplayName("재고가 충분하고 캐시가 없을 때 db에 조회 후 캐시에 저장")
	void shouldQueryDbAndCacheStockWhen_StockIsSufficientAndCacheNotExists() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.TEN);
		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		ProductVariant productVariant = TestDomainFactory.createProductVariant(
			productVariantId.getId(), UUID.randomUUID()
		);
		when(readProductVariantPort.readByIdList(List.of(productVariantId)))
			.thenReturn(List.of(productVariant));

		// when
		validateProductStockUseCase.execute(input);

		// then
		verify(readProductVariantPort, times(1))
			.readByIdList(List.of(productVariantId));
		String cachedStock = redisTemplate.opsForValue()
			.get(STOCK_CACHE_KEY_PREFIX + productVariantId.getId());
		assertThat(cachedStock).isEqualTo("90");
	}

	@Test
	@DisplayName("재고가 충분하고 캐시에 값이 있을때 db 조회 없이 캐시 사용")
	void shouldUseCacheWhenStockIsSufficientAndCacheExists() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.TEN);
		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		String cacheKey = STOCK_CACHE_KEY_PREFIX + productVariantId.getId();
		redisTemplate.opsForValue()
			.set(cacheKey, "20");

		// when
		validateProductStockUseCase.execute(input);

		// then
		verify(readProductVariantPort, never())
			.readByIdList(List.of(productVariantId));

		String cachedStock = redisTemplate.opsForValue()
			.get(cacheKey);
		assertThat(cachedStock).isEqualTo("10");
	}

	@Test
	@DisplayName("여러 상품 동시 처리")
	void shouldHandleMultipleProductsWhenProcessingSimultaneously() {
		// given
		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity1 = new Quantity(BigDecimal.TEN);
		Quantity requestQuantity2 = new Quantity(BigDecimal.ONE);

		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId1)
						.quantity(requestQuantity1)
						.build(),
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId2)
						.quantity(requestQuantity2)
						.build()
				)
			)
			.build();

		String cacheKey1 = STOCK_CACHE_KEY_PREFIX + productVariantId1.getId();
		redisTemplate.opsForValue().set(cacheKey1, "25");

		ProductVariant productVariant2 = TestDomainFactory.createProductVariant(
			productVariantId2.getId(), UUID.randomUUID()
		);
		when(readProductVariantPort.readByIdList(List.of(productVariantId2)))
			.thenReturn(List.of(productVariant2));

		// when
		validateProductStockUseCase.execute(input);

		// then
		verify(readProductVariantPort, never())
			.readByIdList(List.of(productVariantId1));
		verify(readProductVariantPort, times(1))
			.readByIdList(List.of(productVariantId2));

		String cacheStock1 = redisTemplate.opsForValue().get(cacheKey1);
		assertThat(cacheStock1).isEqualTo("15");

		String cacheKey2 = STOCK_CACHE_KEY_PREFIX + productVariantId2.getId();
		String cacheStock2 = redisTemplate.opsForValue().get(cacheKey2);
		assertThat(cacheStock2).isEqualTo("99");
	}

	@Test
	@DisplayName("db 조회시 재고 부족 예외 발생")
	void shouldThrowExceptionWhenStockNotEnoughInDb() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.valueOf(110));
		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		ProductVariant productVariant = TestDomainFactory.createProductVariant(
			productVariantId.getId(), UUID.randomUUID()
		);
		when(readProductVariantPort.readByIdList(List.of(productVariantId)))
			.thenReturn(List.of(productVariant));

		// when
		// then
		assertThatThrownBy(() -> validateProductStockUseCase.execute(input))
			.isInstanceOf(ItemWebException.class);

		verify(readProductVariantPort, times(1))
			.readByIdList(List.of(productVariantId));
		String cachedStock = redisTemplate.opsForValue().get(STOCK_CACHE_KEY_PREFIX + productVariantId.getId());
		assertThat(cachedStock).isNull();
	}

	@Test
	@DisplayName("캐시 조회시 재고 부족 예외 발생")
	void shouldThrowExceptionWhenStockNotEnoughInCache() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.TEN);
		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		String cacheKey = STOCK_CACHE_KEY_PREFIX + productVariantId.getId();
		redisTemplate.opsForValue().set(cacheKey, "5");

		// when
		// then
		assertThatThrownBy(() -> validateProductStockUseCase.execute(input))
			.isInstanceOf(ItemWebException.class);
		verify(readProductVariantPort, never())
			.readByIdList(List.of(productVariantId));

		String cacheStock = redisTemplate.opsForValue().get(cacheKey);
		assertThat(cacheStock).isEqualTo("5");
	}

	@Test
	@DisplayName("캐시에 값이 충분히 있을때 단일 상품 동시성 100명 테스트")
	void shouldDeductStockCorrectlyWhen100UsersRequestAtSameTime() throws InterruptedException {
		// given
		int numberOfThreads = 100;
		int initialStock = 100;
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.ONE);

		String cacheKey = STOCK_CACHE_KEY_PREFIX + productVariantId.getId();
		redisTemplate.opsForValue().set(cacheKey, String.valueOf(initialStock));

		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					validateProductStockUseCase.execute(input);
					successCount.incrementAndGet();
				} catch (ItemWebException e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// then
		String cacheStock = redisTemplate.opsForValue().get(cacheKey);
		assertThat(cacheStock).isEqualTo("0");
		assertThat(successCount.get()).isEqualTo(100);
		assertThat(failCount.get()).isZero();
	}

	@Test
	@DisplayName("캐시에 값이 부족하게 있을때 단일 상품 동시성 100명 테스트 실패")
	void shouldHandlePartialSuccessWhenConcurrentRequestsExceedStock() throws InterruptedException {
		// given
		int numberOfThreads = 100;
		int initialStock = 50;
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.ONE);

		String cacheKey = STOCK_CACHE_KEY_PREFIX + productVariantId.getId();
		redisTemplate.opsForValue().set(cacheKey, String.valueOf(initialStock));

		ValidateProductStockUseCaseDto.Input input = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					validateProductStockUseCase.execute(input);
					successCount.incrementAndGet();
				} catch (ItemWebException e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// then
		String cacheStock = redisTemplate.opsForValue().get(cacheKey);
		assertThat(cacheStock).isEqualTo("0");
		assertThat(successCount.get()).isEqualTo(50);
		assertThat(failCount.get()).isEqualTo(50);
	}

	@Test
	@DisplayName("캐시에 값이 충분히 있을때 2개 상품 동시성 100명 테스트")
	void shouldHandleIndependentLocksWhenMultipleProductsConcurrentRequests() throws InterruptedException {
		int numberOfThreads = 100;
		int initialStock = 50;
		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());
		Quantity requestQuantity = new Quantity(BigDecimal.ONE);

		String cacheKey1 = STOCK_CACHE_KEY_PREFIX + productVariantId1.getId();
		String cacheKey2 = STOCK_CACHE_KEY_PREFIX + productVariantId2.getId();

		redisTemplate.opsForValue().set(cacheKey1, String.valueOf(initialStock));
		redisTemplate.opsForValue().set(cacheKey2, String.valueOf(initialStock));

		ValidateProductStockUseCaseDto.Input input1 = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId1)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();
		ValidateProductStockUseCaseDto.Input input2 = ValidateProductStockUseCaseDto.Input.builder()
			.checkProductList(
				List.of(
					ValidateProductStockUseCaseDto.ProductQuantityDto.builder()
						.productVariantId(productVariantId2)
						.quantity(requestQuantity)
						.build()
				)
			)
			.build();

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			int threadId = i;
			executorService.submit(() -> {
				try {
					if (threadId % 2 == 0) {
						validateProductStockUseCase.execute(input1);
					} else {
						validateProductStockUseCase.execute(input2);
					}
					successCount.incrementAndGet();
				} catch (QuantityException e) {
					failCount.incrementAndGet();
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// then
		String cacheStock1 = redisTemplate.opsForValue().get(cacheKey1);
		String cacheStock2 = redisTemplate.opsForValue().get(cacheKey2);

		assertThat(cacheStock1).isEqualTo("0");
		assertThat(cacheStock2).isEqualTo("0");
		assertThat(successCount.get()).isEqualTo(100);
		assertThat(failCount.get()).isZero();
	}

}
