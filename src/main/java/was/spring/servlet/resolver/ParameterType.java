package was.spring.servlet.resolver;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum ParameterType {
    STRING(type -> type == String.class, value -> value),
    LONG(type -> type == Long.class || type == long.class, Long::parseLong),
    INTEGER(type -> type == Integer.class || type == int.class, Integer::parseInt),
    OBJECT(type -> type == Object.class, value -> value);

    private final Predicate<Class<?>> isMatch;

    private final Function<String, Object> convert;

    ParameterType(Predicate<Class<?>> isMatch, Function<String, Object> convert) {
        this.isMatch = isMatch;
        this.convert = convert;
    }

    public Object convert(String value) {
        return convert.apply(value);
    }

    public static boolean isStandardType(Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(parameterType -> parameterType.isMatch.test(type) && parameterType != OBJECT);
    }

    public static ParameterType from(Class<?> type) {
        return Arrays.stream(values())
                .filter(parameterType -> parameterType.isMatch.test(type))
                .findFirst()
                .orElse(OBJECT);
    }
}
