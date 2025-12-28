package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.usecase.dto.DeleteOptionGroupUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteOptionGroupUseCaseTest {

	private DeleteOptionGroupUseCase deleteOptionGroupUseCase;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private DeleteOptionGroupPort mockDeleteOptionGroupPort;

	@BeforeEach
	void setUp() {
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockDeleteOptionGroupPort = mock(DeleteOptionGroupPort.class);
		deleteOptionGroupUseCase = new DeleteOptionGroupUseCase(
			mockReadOptionGroupPort,
			mockDeleteOptionGroupPort
		);
	}

	@Test
	@DisplayName("옵션 그룹 삭제")
	void deleteOptionGroup() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionGroupUseCaseDto.Input input = DeleteOptionGroupUseCaseDto.Input.builder()
			.optionGroupId(optionGroupId)
			.userId(userId)
			.build();

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			UUID.randomUUID(),
			userId.getId()
		);

		when(mockReadOptionGroupPort.readOrThrow(any()))
			.thenReturn(optionGroup);

		// when
		deleteOptionGroupUseCase.execute(input);

		// then
		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);
		verify(mockDeleteOptionGroupPort, times(1))
			.delete(optionGroup);
	}

	@Test
	@DisplayName("옵션 그룹의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionGroupUseCaseDto.Input input = DeleteOptionGroupUseCaseDto.Input.builder()
			.optionGroupId(optionGroupId)
			.userId(userId)
			.build();

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			UUID.randomUUID(),
			UUID.randomUUID()
		);

		when(mockReadOptionGroupPort.readOrThrow(any()))
			.thenReturn(optionGroup);

		// when
		// then
		assertThatThrownBy(() -> deleteOptionGroupUseCase.execute(input))
			.isInstanceOf(OptionException.class);
		verify(mockDeleteOptionGroupPort, never())
			.delete(optionGroup);
	}

}
