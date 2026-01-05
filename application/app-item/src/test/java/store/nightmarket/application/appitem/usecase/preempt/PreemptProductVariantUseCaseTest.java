package store.nightmarket.application.appitem.usecase.preempt;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
import org.springframework.test.context.ActiveProfiles;

import store.nightmarket.application.appitem.out.preempt.ReadPreemptedProductVariantPort;
import store.nightmarket.application.appitem.usecase.preempt.dto.PreemptProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;

@ActiveProfiles("test")
@SpringBootTest
public class PreemptProductVariantUseCaseTest {

	@Autowired
	private PreemptProductVariantUseCase preemptProductVariantUseCase;

	@Autowired
	private ReadPreemptedProductVariantPort readPreemptedProductVariantPort;

	@Autowired
	private ProductVariantRepository productVariantRepository;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@BeforeEach
	void setUp() {
		Objects.requireNonNull(redisTemplate.getConnectionFactory())
			.getConnection()
			.serverCommands()
			.flushAll();
	}

	@Test
	@DisplayName("단일 상품 구매 동시성 50명 테스트")
	void shouldDeductSingleProductStockCorrectlyWhen50UsersRequestAtSameTime() throws InterruptedException {
		// given
		int numberOfThreads = 50;
		int requestQuantity = 1;

		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		productVariantRepository.save(ProductVariantEntity.newInstance(
			productVariantId.getId(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			"test",
			new QuantityEntity(BigInteger.valueOf(5))
		));

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					PreemptProductVariantUseCaseDto.Output output = preemptProductVariantUseCase.execute(
						PreemptProductVariantUseCaseDto.Input.builder()
							.orderId(new OrderId(UUID.randomUUID()))
							.preemptRequestedProductList(
								List.of(
									PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
										.productVariantId(productVariantId)
										.quantity(new Quantity(BigInteger.valueOf(requestQuantity)))
										.build()
								)
							)
							.build()
					);
					if (output.isSuccess()) {
						successCount.incrementAndGet();
					} else {
						failCount.incrementAndGet();
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		// then
		Long totalPreempted = readPreemptedProductVariantPort.readPreemptedQuantity(productVariantId.getId(),
			LocalDateTime.now());

		assertThat(totalPreempted).isEqualTo(5);
		assertThat(successCount.get()).isEqualTo(5);
		assertThat(failCount.get()).isEqualTo(45);
	}

	@Test
	@DisplayName("다중 상품 구매 동시성 50명 테스트")
	void shouldDeductSMultiProductStockCorrectlyWhen50UsersRequestAtSameTime() throws InterruptedException {
		// given
		int numberOfThreads = 50;
		int requestQuantity = 1;

		ProductVariantId productVariantIdA = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantIdB = new ProductVariantId(UUID.randomUUID());

		productVariantRepository.save(
			ProductVariantEntity.newInstance(
				productVariantIdA.getId(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				"A",
				new QuantityEntity(BigInteger.valueOf(5))
			)
		);

		productVariantRepository.save(
			ProductVariantEntity.newInstance(
				productVariantIdB.getId(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				"B",
				new QuantityEntity(BigInteger.valueOf(5))
			)
		);

		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			executorService.submit(() -> {
				try {
					PreemptProductVariantUseCaseDto.Output output = preemptProductVariantUseCase.execute(
						PreemptProductVariantUseCaseDto.Input.builder()
							.orderId(new OrderId(UUID.randomUUID()))
							.preemptRequestedProductList(
								List.of(
									PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
										.productVariantId(productVariantIdA)
										.quantity(new Quantity(BigInteger.valueOf(requestQuantity)))
										.build(),
									PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
										.productVariantId(productVariantIdB)
										.quantity(new Quantity(BigInteger.valueOf(requestQuantity)))
										.build()
								)
							)
							.build()
					);
					if (output.isSuccess()) {
						successCount.incrementAndGet();
					} else {
						failCount.incrementAndGet();
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		// then
		Long totalPreemptedA = readPreemptedProductVariantPort.readPreemptedQuantity(productVariantIdA.getId(),
			LocalDateTime.now());
		Long totalPreemptedB = readPreemptedProductVariantPort.readPreemptedQuantity(productVariantIdB.getId(),
			LocalDateTime.now());

		assertThat(totalPreemptedA).isEqualTo(5);
		assertThat(totalPreemptedB).isEqualTo(5);
		assertThat(successCount.get()).isEqualTo(5);
		assertThat(failCount.get()).isEqualTo(45);
	}

	@Test
	@DisplayName("재고 부족 시 부족한 상품 아이디를 반환한다")
	void shouldThrowExceptionWhenOutOfStock() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());

		productVariantRepository.save(
			ProductVariantEntity.newInstance(
				productVariantId.getId(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				"test-product",
				new QuantityEntity(BigInteger.valueOf(5))
			)
		);

		PreemptProductVariantUseCaseDto.Input input =
			PreemptProductVariantUseCaseDto.Input.builder()
				.orderId(new OrderId(UUID.randomUUID()))
				.preemptRequestedProductList(
					List.of(
						PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
							.productVariantId(productVariantId)
							.quantity(new Quantity(BigInteger.valueOf(6)))
							.build()
					)
				)
				.build();

		// when
		PreemptProductVariantUseCaseDto.Output output = preemptProductVariantUseCase.execute(input);

		// then
		assertThat(output.isSuccess()).isFalse();
		assertThat(output.insufficientProductList().getFirst()).isEqualTo(productVariantId);
	}

	@Test
	@DisplayName("재고 충분 시 부족한 상품아이디를 반환하지 않는다")
	void shouldNotReturnProductVariantIdWhenStockEnough() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());

		productVariantRepository.save(
			ProductVariantEntity.newInstance(
				productVariantId.getId(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				"test-product",
				new QuantityEntity(BigInteger.valueOf(5))
			)
		);

		PreemptProductVariantUseCaseDto.Input input =
			PreemptProductVariantUseCaseDto.Input.builder()
				.orderId(new OrderId(UUID.randomUUID()))
				.preemptRequestedProductList(
					List.of(
						PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
							.productVariantId(productVariantId)
							.quantity(new Quantity(BigInteger.valueOf(1)))
							.build()
					)
				)
				.build();

		// when
		PreemptProductVariantUseCaseDto.Output output = preemptProductVariantUseCase.execute(input);

		// then
		assertThat(output.isSuccess()).isTrue();
		assertThat(output.insufficientProductList().size()).isEqualTo(0);
	}

}
