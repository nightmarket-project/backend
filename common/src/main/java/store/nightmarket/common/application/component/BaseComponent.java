package store.nightmarket.common.application.component;

public interface BaseComponent<Input, Output> {

	Output execute(Input input);

}
