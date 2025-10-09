package store.nightmarket.application.appitem.aop;

import java.util.Arrays;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringElpParser {

	private CustomSpringElpParser() {
	}

	public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();

		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}

		Object value = parser.parseExpression(key).getValue(context);

		if (value == null) {
			throw new IllegalArgumentException(
				"SpEL expression '" + key + "' evaluated to null. " +
					"Available parameters: " + Arrays.toString(parameterNames)
			);
		}

		return value;
	}

}
