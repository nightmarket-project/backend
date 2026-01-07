package store.nightmarket.domain.item.service;

import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.fixture.TestFactory;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.service.dto.ModifyOptionGroupDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;

public class ModifyOptionGroupDomainServiceTest {

	private ModifyOptionGroupDomainService service;
	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new ModifyOptionGroupDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("서비스 실행시 값이 변경되고 변경된 옵션그룹이 반환된다")
	void shouldReturnUpdatedOptionGroupWhenExecuteService() {
		// given
		UUID optionGroupId = UUID.randomUUID();
		String editName = "옵션 그룹2";
		int editOrder = 2;

		OptionGroup optionGroup = TestFactory.createOptionGroup(
			optionGroupId,
			UUID.randomUUID(),
			"옵션 그룹1",
			1
		);

		ModifyOptionGroupDomainServiceDto.Input input = ModifyOptionGroupDomainServiceDto.Input.builder()
			.optionGroup(optionGroup)
			.name(new Name(editName))
			.order(editOrder)
			.build();

		// when
		ModifyOptionGroupDomainServiceDto.Event event = service.execute(input);

		// then
		softly.assertThat(event).isNotNull();
		softly.assertThat(event.optionGroup().getName().getValue()).isEqualTo(editName);
		softly.assertThat(event.optionGroup().getOrder()).isEqualTo(editOrder);
		softly.assertAll();
	}

}
