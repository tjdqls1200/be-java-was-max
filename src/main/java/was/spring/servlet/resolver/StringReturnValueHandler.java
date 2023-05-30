package was.spring.servlet.resolver;

import was.spring.servlet.common.exception.NoSuchViewException;
import was.spring.servlet.http.HttpStatus;
import was.spring.servlet.mvc.controller.ResponseStatus;
import was.spring.servlet.mvc.view.Model;
import was.spring.servlet.mvc.view.ModelAndView;

import java.lang.reflect.Method;

public class StringReturnValueHandler {
    public ModelAndView handle(Method method, Object returnValue, Model model) {
        if (returnValue.getClass() == ModelAndView.class) {
            if (isEmptyViewName((ModelAndView) returnValue)) {
                throw new NoSuchViewException();
            }

            return (ModelAndView) returnValue;
        }

        String viewName = (String) returnValue;
        HttpStatus httpStatus = getHttpStatus(method);

        return new ModelAndView(model, viewName, httpStatus);
    }

    private boolean isEmptyViewName(ModelAndView returnValue) {
        return returnValue.getViewName() == null;
    }

    private HttpStatus getHttpStatus(Method method) {
        if (method.isAnnotationPresent(ResponseStatus.class)) {
            return method.getDeclaredAnnotation(ResponseStatus.class).status();
        }

        return HttpStatus.OK;
    }
}
