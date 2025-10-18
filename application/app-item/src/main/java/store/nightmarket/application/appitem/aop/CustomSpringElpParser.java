package store.nightmarket.application.appitem.aop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringElpParser {

	private CustomSpringElpParser() {
	}

	// 단일 키 처리
	// public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
	// 	ExpressionParser parser = new SpelExpressionParser();
	// 	StandardEvaluationContext context = new StandardEvaluationContext();
	//
	// 	for (int i = 0; i < parameterNames.length; i++) {
	// 		context.setVariable(parameterNames[i], args[i]);
	// 	}
	//
	// 	Object value = parser.parseExpression(key).getValue(context);
	//
	// 	if (value == null) {
	// 		throw new IllegalArgumentException(
	// 			"SpEL expression '" + key + "' evaluated to null. " +
	// 				"Available parameters: " + Arrays.toString(parameterNames)
	// 		);
	// 	}
	//
	// 	return value;
	// }

	// 복합키 처리
	public static List<Object> getDynamicValue(String[] parameterNames, Object[] args, String[] keys) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();

		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}

		return Arrays.stream(keys)
			.map(key -> {
				try {
					Object value = parser.parseExpression(key).getValue(context);

					if (value == null) {
						throw new IllegalArgumentException(
							String.format("SpEL expression '%s' evaluated to null. Available parameters: %s",
								key, Arrays.toString(parameterNames))
						);
					}

					return value;
				} catch (Exception e) {
					throw new IllegalArgumentException(
						String.format("Failed to evaluate SpEL expression '%s': %s. Available parameters: %s",
							key, e.getMessage(), Arrays.toString(parameterNames)),
						e
					);
				}
			})
			.flatMap(value -> {
				if (value instanceof List) {
					return ((List<?>)value).stream();
				}
				return Stream.of(value);
			})
			.toList();
	}

}
