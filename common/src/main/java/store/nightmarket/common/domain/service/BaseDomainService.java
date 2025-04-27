package store.nightmarket.common.domain.service;

import org.springframework.stereotype.Component;

public interface BaseDomainService<Input, Output> {

	Output execute(Input input);

}
