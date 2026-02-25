package store.nightmarket.application.appoutbox.out.adpater;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appoutbox.out.SaveOutboxPort;
import store.nightmarket.application.appoutbox.out.mapper.OutboxMapper;
import store.nightmarket.domain.outbox.model.Outbox;
import store.nightmarket.persistence.persistoutbox.repository.OutboxEntityRepository;

@Component
@RequiredArgsConstructor
public class SaveOutboxJpaAdapter implements SaveOutboxPort {

	public final OutboxEntityRepository outboxEntityRepository;

	@Override
	public void save(Outbox outbox) {
		outboxEntityRepository.save(OutboxMapper.toEntity(outbox));
	}

	@Override
	public void saveWithId(Outbox outbox) {
		outboxEntityRepository.save(OutboxMapper.toEntityWithId(outbox));
	}

}
