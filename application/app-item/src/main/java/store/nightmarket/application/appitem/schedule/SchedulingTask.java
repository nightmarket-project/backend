package store.nightmarket.application.appitem.schedule;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchedulingTask<T> implements Runnable {

	private final T target;
	private final Consumer<T> task;

	@Override
	public void run() {
		task.accept(target);
	}

}
