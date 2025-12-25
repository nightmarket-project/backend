package store.nightmarket.application.appitem.aop;

import static store.nightmarket.application.appitem.constant.Constant.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class DistributedLockSpelParser {

	private DistributedLockSpelParser() {
	}

	public static List<String> getDynamicValue(
		String[] parameterNames,
		Object[] args,
		String[] expressions
	) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		setContext(parameterNames, args, context);

		return Arrays.stream(expressions)
			.map(expr -> parseValue(expr, parser, context))
			.flatMap(DistributedLockSpelParser::flatten)
			.map(DistributedLockSpelParser::toLockKey)
			.distinct()
			.sorted()
			.toList();
	}

	private static Object parseValue(
		String expression,
		ExpressionParser parser,
		StandardEvaluationContext context
	) {
		try {
			return parser.parseExpression(expression).getValue(context);
		} catch (Exception e) {
			throw new IllegalArgumentException(
				"Failed to evaluate SpEL expression: " + expression, e
			);
		}
	}

	private static Stream<?> flatten(Object value) {
		if (value instanceof Iterable<?> iterable) {
			return StreamSupport.stream(iterable.spliterator(), false);
		}
		return Stream.of(value);
	}

	private static String toLockKey(Object value) {
		if (value instanceof UUID uuid) {
			return LOCK_PREFIX + uuid;
		}
		if (value instanceof String str) {
			return LOCK_PREFIX + str;
		}
		if (value instanceof Number num) {
			return LOCK_PREFIX + num;
		}

		throw new IllegalArgumentException(
			"Unsupported lock key type: " + value.getClass().getName()
		);
	}

	private static void setContext(
		String[] parameterNames,
		Object[] args,
		StandardEvaluationContext context
	) {
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
	}

}
