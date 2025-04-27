package store.nightmarket.common.domain.service;

public interface BaseDomainService<Input, Event> {

	Event execute(Input input);

}
