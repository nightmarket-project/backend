package store.nightmarket.domain.item.service;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.fixture.TestFactory;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.service.dto.ModifyProductDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyProductDomainServiceTest {

	private ModifyProductDomainService service;
	public SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new ModifyProductDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("서비스 실행시 값이 변경되고 변경된 상품이 반환된다")
	void shouldReturnUpdatedProductWhenExecuteService() {
		// given
		UUID productId = UUID.randomUUID();
		String editName = "변경된상품";
		String editDescription = "변경된설명";
		int editPrice = 2000;

		Product product = TestFactory.createProduct(
			productId,
			UUID.randomUUID(),
			"변경전상품",
			"변경전설명",
			1000
		);

		ModifyProductDomainServiceDto.Input input = ModifyProductDomainServiceDto.Input.builder()
			.product(product)
			.name(new Name(editName))
			.description(editDescription)
			.price(new Price(BigInteger.valueOf(editPrice)))
			.build();

		// when
		ModifyProductDomainServiceDto.Event event = service.execute(input);

		// then
		softly.assertThat(event).isNotNull();
		softly.assertThat(event.product().getName().getValue()).isEqualTo(editName);
		softly.assertThat(event.product().getDescription()).isEqualTo(editDescription);
		softly.assertThat(event.product().getPrice().amount()).isEqualTo(editPrice);
		softly.assertAll();
	}

}
