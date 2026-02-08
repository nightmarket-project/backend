package store.nightmarket.application.appuser.out.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.out.ReadOutboxPort;
import store.nightmarket.application.appuser.out.mapper.OutboxMapper;
import store.nightmarket.domain.user.model.Outbox;
import store.nightmarket.persistence.persistuser.repository.OutboxEntityRepository;

@Component
@RequiredArgsConstructor
public class ReadOutboxJpaAdapter implements ReadOutboxPort {

	private final OutboxEntityRepository outboxEntityRepository;

	@Override
	public Optional<Outbox> read(Long id) {
		return outboxEntityRepository.findById(id)
			.map(OutboxMapper::toDomain);
	}

	@Override
	public List<Outbox> readPublishTarget() {
		return outboxEntityRepository.findPublishTarget()
			.stream()
			.map(OutboxMapper::toDomain)
			.toList();
	}

}
