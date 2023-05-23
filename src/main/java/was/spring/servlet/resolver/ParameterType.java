package was.spring.servlet.resolver;

import java.util.Arrays;

public enum ParameterType {
    STRING(String.class),
    LONG(Long.class),
    INTEGER(Integer.class),
    OBJECT(Object.class);

    private final Class<?> type;

    ParameterType(Class<?> type) {
        this.type = type;
    }

    public static ParameterType from(Class<?> type) {
        return Arrays.stream(values())
                .filter(parameterType -> parameterType.type == type)
                .findFirst()
                .orElse(OBJECT);
    }
}
