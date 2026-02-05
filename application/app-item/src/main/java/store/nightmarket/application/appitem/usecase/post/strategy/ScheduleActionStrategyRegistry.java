package store.nightmarket.application.appitem.usecase.post.strategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import store.nightmarket.application.appitem.usecase.post.strategy.payload.ScheduleActionPayload;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.state.ScheduleActionType;

@Component
public class ScheduleActionStrategyRegistry {

	private final Map<ScheduleActionType, ScheduleActionStrategy> strategies;

	public ScheduleActionStrategyRegistry(List<ScheduleActionStrategy> strategies) {
		this.strategies = strategies.stream()
			.collect(Collectors.toMap(
				ScheduleActionStrategy::getType,
				Function.identity()
			));
	}

	public <P extends ScheduleActionPayload> ScheduleActionStrategy<P> get(ScheduleActionType type) {
		ScheduleActionStrategy<P> strategy = strategies.get(type);

		if (strategy == null) {
			throw new ProductPostException("No strategy for type: " + type);
		}

		return strategy;
	}

}