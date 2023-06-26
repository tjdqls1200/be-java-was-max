package was.spring.servlet.resolver;

import java.lang.reflect.Field;
import java.util.Map;

public final class ObjectBinder {
    private static final String SETTER_PREFIX = "set";
    private static final int FIRST_CHAR_INDEX = 0;
    private static final int SECOND_CHAR_INDEX = 1;


    public Object mapToBind(Class<?> objectType, Map<String, String> args) throws ReflectiveOperationException {
        final Object object = objectType.getDeclaredConstructor().newInstance();

        for (Field field : objectType.getDeclaredFields()) {
            String fieldName = field.getName();
            String setterName = formatSetterName(fieldName);
            Object argumentValue = getArgumentValue(field, args.get(fieldName));

            try {
                objectType.getDeclaredMethod(setterName, field.getType()).invoke(object, argumentValue);
            } catch (NoSuchMethodException ignored) {
            }
        }

        return object;
    }

    private Object getArgumentValue(Field field, String value) {
        return ParameterType.from(field.getType()).convert(value);
    }

    private String formatSetterName(String fieldName) {
        return SETTER_PREFIX + Character.toUpperCase(fieldName.charAt(FIRST_CHAR_INDEX)) + fieldName.substring(SECOND_CHAR_INDEX);
    }
}
