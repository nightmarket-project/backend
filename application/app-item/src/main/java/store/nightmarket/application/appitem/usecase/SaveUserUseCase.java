package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.SaveUserUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveUserPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.User;

@Service
@RequiredArgsConstructor
public class SaveUserUseCase implements BaseUseCase<Input, Void> {

	private final SaveUserPort saveUserPort;

	@Override
	public Void execute(Input input) {
		User user = User.newInstance(
			input.userId(),
			input.name()
		);

		saveUserPort.save(user);
		return null;
	}

}
