package store.nightmarket.application.appitem.usecase.post.schedule.strategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.state.SchedulePostActionType;

@Component
public class SchedulePostActionStrategyRegistry {

	private final Map<SchedulePostActionType, SchedulePostActionStrategy> strategies;

	public SchedulePostActionStrategyRegistry(List<SchedulePostActionStrategy> strategies) {
		this.strategies = strategies.stream()
			.collect(Collectors.toMap(
				SchedulePostActionStrategy::getType,
				Function.identity()
			));
	}

	public SchedulePostActionStrategy get(SchedulePostActionType type) {
		SchedulePostActionStrategy strategy = strategies.get(type);

		if (strategy == null) {
			throw new ProductPostException("No strategy for type: " + type);
		}

		return strategy;
	}

}