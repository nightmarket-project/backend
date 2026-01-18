package store.nightmarket.domain.item.service;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.fixture.TestFactory;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.service.dto.ModifyProductVariantDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ModifyProductVariantDomainServiceTest {

	private ModifyProductVariantDomainService service;
	public SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new ModifyProductVariantDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("서비스 실행시 값이 변경되고 변경된 ProductVariant가 반환된다")
	void shouldReturnUpdatedProductVariantWhenExecuteService() {
		// given
		UUID productVariantId = UUID.randomUUID();
		String editSKUCode = "변경된코드";
		Quantity editQuantity = new Quantity(BigInteger.valueOf(2));

		ProductVariant productVariant = TestFactory.createProductVariant(
			productVariantId,
			UUID.randomUUID(),
			UUID.randomUUID(),
			"변경전코드",
			1
		);

		ModifyProductVariantDomainServiceDto.Input input = ModifyProductVariantDomainServiceDto.Input.builder()
			.productVariant(productVariant)
			.SKUCode(editSKUCode)
			.quantity(editQuantity)
			.build();

		// when
		ModifyProductVariantDomainServiceDto.Event event = service.execute(input);

		// then
		softly.assertThat(event).isNotNull();
		softly.assertThat(event.productVariant().getSKUCode()).isEqualTo(editSKUCode);
		softly.assertThat(event.productVariant().getQuantity()).isEqualTo(editQuantity);
		softly.assertAll();
	}

}
