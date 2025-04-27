package store.nightmarket.common.domain.service;

public interface BaseDomainService<Command, Event> {

	Event execute(Command command);

}
