package was.spring.servlet.mvc.view;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final Map<String, Object> attributes;

    public Model() {
        attributes = new HashMap<>();
    }

    public Model(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
