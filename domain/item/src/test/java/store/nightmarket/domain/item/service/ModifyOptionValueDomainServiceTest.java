package store.nightmarket.domain.item.service;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.fixture.TestFactory;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.service.dto.ModifyOptionValueDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyOptionValueDomainServiceTest {

	private ModifyOptionValueDomainService service;
	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new ModifyOptionValueDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("서비스 실행시 값이 변경되고 변경된 옵션밸류가 반환된다")
	void shouldReturnUpdatedOptionGroupWhenExecuteService() {
		// given
		UUID optionGroupId = UUID.randomUUID();
		String editName = "옵션 그룹2";
		int editPrice = 2000;
		int editOrder = 2;

		OptionValue optionValue = TestFactory.createOptionValue(
			optionGroupId,
			UUID.randomUUID(),
			"옵션 그룹1",
			1000,
			1
		);

		ModifyOptionValueDomainServiceDto.Input input = ModifyOptionValueDomainServiceDto.Input.builder()
			.optionValue(optionValue)
			.name(new Name(editName))
			.price(new Price(BigInteger.valueOf(editPrice)))
			.order(editOrder)
			.build();

		// when
		ModifyOptionValueDomainServiceDto.Event event = service.execute(input);

		// then
		softly.assertThat(event).isNotNull();
		softly.assertThat(event.optionValue().getName().getValue()).isEqualTo(editName);
		softly.assertThat(event.optionValue().getPrice().amount()).isEqualTo(editPrice);
		softly.assertThat(event.optionValue().getOrder()).isEqualTo(editOrder);
		softly.assertAll();
	}

}
