package store.nightmarket.application.appuser.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.out.SaveOutboxPort;
import store.nightmarket.application.appuser.out.mapper.OutboxMapper;
import store.nightmarket.domain.user.model.Outbox;
import store.nightmarket.persistence.persistuser.repository.OutboxEntityRepository;

@Component
@RequiredArgsConstructor
public class SaveOutboxJpaAdapter implements SaveOutboxPort {

	public final OutboxEntityRepository outboxEntityRepository;

	@Override
	public void save(Outbox outbox) {
		outboxEntityRepository.save(OutboxMapper.toEntity(outbox));
	}

}
