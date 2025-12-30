package store.nightmarket.application.appitem.usecase;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionValuePort;
import store.nightmarket.application.appitem.usecase.dto.DeleteOptionValueUseCaseDto;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteOptionValueUseCaseTest {

	private DeleteOptionValueUseCase deleteOptionValueUseCase;
	private ReadOptionValuePort mockReadOptionValuePort;
	private DeleteOptionValuePort mockDeleteOptionValuePort;

	@BeforeEach
	void setUp() {
		mockReadOptionValuePort = mock(ReadOptionValuePort.class);
		mockDeleteOptionValuePort = mock(DeleteOptionValuePort.class);
		deleteOptionValueUseCase = new DeleteOptionValueUseCase(
			mockReadOptionValuePort,
			mockDeleteOptionValuePort
		);
	}

	@Test
	@DisplayName("옵션 밸류 삭제")
	void deleteOptionValue() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		OptionValueId optionValueId = new OptionValueId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionValueUseCaseDto.Input input = DeleteOptionValueUseCaseDto.Input.builder()
			.optionGroupId(optionGroupId)
			.optionValueId(optionValueId)
			.userId(userId)
			.build();

		OptionValue optionValue = TestDomainFactory.createOptionValue(
			optionValueId.getId(),
			optionGroupId.getId()
		);

		when(mockReadOptionValuePort.readOrThrow(any()))
			.thenReturn(optionValue);

		// when
		deleteOptionValueUseCase.execute(input);

		// then
		verify(mockReadOptionValuePort, times(1))
			.readOrThrow(optionValueId);
		verify(mockDeleteOptionValuePort, times(1))
			.delete(optionValue.getOptionValueId());
	}

}
