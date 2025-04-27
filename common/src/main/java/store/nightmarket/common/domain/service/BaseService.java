package store.nightmarket.common.domain.service;

import org.springframework.stereotype.Component;

@Component
public interface BaseService<Input, Output> {

	Output execute(Input input);

}
