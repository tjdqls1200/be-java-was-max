package was.container;

import was.common.HttpMethod;
import was.container.mvc.controller.proxy.ControllerProxy;

import java.util.function.BiPredicate;

public interface RequestMapping {
    String getPrifix();

    ControllerProxy getController();

    BiPredicate<String, HttpMethod> getPattern();

    default boolean isMapped(String requestUrl, HttpMethod method) {
        return getPattern().test(requestUrl, method);
    }
}
