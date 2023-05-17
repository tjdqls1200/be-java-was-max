package was.container;

import was.common.HttpMethod;
import was.container.mvc.controller.proxy.ControllerProxy;
import was.container.mvc.controller.proxy.UserControllerProxy;

import java.util.function.BiPredicate;

public enum UserRequestMapping implements RequestMapping {
    JOIN("/users", (requsetUrl, method) -> "/users".matches(requsetUrl) && method == HttpMethod.POST),
    READ_USER("/users/", (requsetUrl, method) -> "/users/\\d+$".matches(requsetUrl) && method == HttpMethod.GET),
    READ_USERS("/users", (requsetUrl, method) -> "/users".matches(requsetUrl) && method == HttpMethod.GET);

    private static final ControllerProxy CONTROLLER = new UserControllerProxy();

    private final BiPredicate<String, HttpMethod> pattern;
    private final String prifix;

    UserRequestMapping(String prifix, BiPredicate<String, HttpMethod> pattern) {
        this.prifix = prifix;
        this.pattern = pattern;
    }

    public BiPredicate<String, HttpMethod> getPattern() {
        return pattern;
    }

    @Override
    public String getPrifix() {
        return prifix;
    }

    @Override
    public ControllerProxy getController() {
        return CONTROLLER;
    }
}
