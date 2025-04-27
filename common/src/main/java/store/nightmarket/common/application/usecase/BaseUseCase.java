package store.nightmarket.common.application.usecase;

import org.springframework.stereotype.Component;

@Component
public interface BaseUseCase<Input, Output> {

	Output execute(Input input);

}
