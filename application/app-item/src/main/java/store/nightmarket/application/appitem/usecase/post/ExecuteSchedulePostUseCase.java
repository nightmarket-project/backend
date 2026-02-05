package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ExecuteSchedulePostUseCaseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadSchedulePostPort;
import store.nightmarket.application.appitem.out.SaveSchedulePostPort;
import store.nightmarket.application.appitem.usecase.post.strategy.ScheduleActionStrategy;
import store.nightmarket.application.appitem.usecase.post.strategy.ScheduleActionStrategyRegistry;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.EmptyPayload;
import store.nightmarket.application.appitem.usecase.post.strategy.payload.ScheduleActionPayload;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.SchedulePost;

@Service
@RequiredArgsConstructor
public class ExecuteSchedulePostUseCase implements BaseUseCase<Input, Void> {

	private final ReadSchedulePostPort readSchedulePostPort;
	private final SaveSchedulePostPort saveSchedulePostPort;
	private final ScheduleActionStrategyRegistry scheduleActionStrategyRegistry;
	private final ObjectMapper objectMapper;

	@Override
	@Transactional
	public Void execute(Input input) {
		SchedulePost schedulePost = readSchedulePostPort.readOrThrow(input.schedulePostId());

		executeAction(schedulePost);

		schedulePost.done();
		saveSchedulePostPort.save(schedulePost);
		return null;
	}

	private <P extends ScheduleActionPayload> void executeAction(SchedulePost schedulePost) {
		ScheduleActionStrategy<P> strategy = scheduleActionStrategyRegistry.get(schedulePost.getType());
		P payload = deserializePayload(strategy, schedulePost);
		strategy.execute(schedulePost, payload);
	}

	private <P extends ScheduleActionPayload> P deserializePayload(
		ScheduleActionStrategy<P> strategy,
		SchedulePost schedulePost
	) {
		if (!strategy.requiresPayload()) {
			return (P)new EmptyPayload();
		}

		if (schedulePost.getContext() == null) {
			throw new ProductPostException("Payload required but context is null. type=" + schedulePost.getType());
		}

		try {
			return objectMapper.readValue(
				schedulePost.getContext(),
				strategy.payloadClass()
			);
		} catch (Exception e) {
			throw new ProductPostException("Failed to deserialize payload. type=" + schedulePost.getType());
		}
	}

}