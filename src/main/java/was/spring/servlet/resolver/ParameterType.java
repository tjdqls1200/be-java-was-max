package was.spring.servlet.resolver;

import java.util.Arrays;
import java.util.function.Function;

public enum ParameterType {
    STRING(String.class, value -> value),
    LONG(Long.class, Long::parseLong),
    INTEGER(Integer.class, Integer::parseInt),
    OBJECT(Object.class, value -> value);

    private final Class<?> type;

    private final Function<String, Object> convert;

    ParameterType(Class<?> type, Function<String, Object> convert) {
        this.type = type;
        this.convert = convert;
    }

    public Object convert(String value) {
        return convert.apply(value);
    }

    public static boolean isStandardType(Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(parameterType -> parameterType.type == type && parameterType != OBJECT);
    }

    public static ParameterType from(Class<?> type) {
        return Arrays.stream(values())
                .filter(parameterType -> parameterType.type == type)
                .findFirst()
                .orElse(OBJECT);
    }
}
