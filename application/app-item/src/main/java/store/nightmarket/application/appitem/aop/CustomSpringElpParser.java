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

	// 복합키 처리
	public static List<Object> getDynamicValue(
            String[] parameterNames,
            Object[] args,
            String[] keys
    ) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
        setContext(parameterNames, args, context);

        return Arrays.stream(keys)
			.map(key -> parseValue(parameterNames, key, parser, context))
			.flatMap(value -> {
				if (value instanceof List) {
					return ((List<?>)value).stream();
				}
				return Stream.of(value);
			})
			.toList();
	}

    private static Object parseValue(
            String[] parameterNames,
            String key,
            ExpressionParser parser,
            StandardEvaluationContext context
    ) {
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
    }

    private static void setContext(String[] parameterNames, Object[] args, StandardEvaluationContext context) {
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
    }

}
