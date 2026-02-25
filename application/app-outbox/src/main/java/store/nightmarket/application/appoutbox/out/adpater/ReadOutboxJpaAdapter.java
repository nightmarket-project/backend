package store.nightmarket.application.appoutbox.out.adpater;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appoutbox.out.ReadOutboxPort;
import store.nightmarket.application.appoutbox.out.mapper.OutboxMapper;
import store.nightmarket.domain.outbox.model.Outbox;
import store.nightmarket.persistence.persistoutbox.repository.OutboxEntityRepository;

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
	public List<Outbox> readPublishTarget(int limit) {
		return outboxEntityRepository.findPublishTarget(limit)
			.stream()
			.map(OutboxMapper::toDomain)
			.toList();
	}

}
