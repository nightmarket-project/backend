package store.nightmarket.application.appitem.schedule;

import java.util.function.Consumer;

import org.springframework.transaction.support.TransactionTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchedulingTask<T> implements Runnable {

	private final T target;
	private final Consumer<T> task;
	private final TransactionTemplate transactionTemplate;

	@Override
	public void run() {
		transactionTemplate.execute(status -> {
			task.accept(target);
			return null;
		});
	}

}
