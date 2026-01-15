package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.mapper.dto.OptionGroupAdapterDto;
import store.nightmarket.application.appitem.usecase.option.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;

class ReadOptionGroupUseCaseTest {

	private ReadOptionGroupUseCase readOptionGroupUseCase;
	private ReadOptionGroupPort mockReadOptionGroupPort;

	@BeforeEach
	void setUp() {
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		readOptionGroupUseCase = new ReadOptionGroupUseCase(mockReadOptionGroupPort);
	}

	@Test
	@DisplayName("제품 아이디를 가지고 옵션 그룹 리스트를 읽는다.")
	void readOptionGroupListWithProductId() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UUID optionGroupId = UUID.randomUUID();
		UUID optionValueId = UUID.randomUUID();

		OptionGroupAdapterDto optionGroupAdapterDto = OptionGroupAdapterDto.builder()
			.optionGroup(TestDomainFactory.createOptionGroup(optionGroupId, productId.getId()))
			.optionValueList(List.of(TestDomainFactory.createOptionValue(optionValueId, optionGroupId)))
			.build();

		when(mockReadOptionGroupPort.readFetchOptionValue(productId))
			.thenReturn(List.of(optionGroupAdapterDto));

		// when
		ReadOptionGroupUseCaseDto.Output output = readOptionGroupUseCase.execute(productId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.optionGroupAdapterDtoList().size())
			.isEqualTo(1);
		assertThat(output.optionGroupAdapterDtoList().getFirst())
			.isEqualTo(optionGroupAdapterDto);
		verify(mockReadOptionGroupPort, times(1))
			.readFetchOptionValue(productId);
	}

}
