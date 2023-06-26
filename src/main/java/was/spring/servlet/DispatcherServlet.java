package was.spring.servlet;

import cafe.mvc.controller.UserController;
import cafe.mvc.repository.UserRepositoryImpl;
import cafe.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;
import was.http.enums.HeaderType;
import was.spring.servlet.common.exception.NoSuchViewException;
import was.spring.servlet.common.HttpStatus;
import was.spring.servlet.mvc.view.ModelAndView;
import was.spring.servlet.mvc.view.View;
import was.spring.servlet.resolver.ViewResolver;

import java.util.List;

public class DispatcherServlet {
    private static final DispatcherServlet DISPATCHER_SERVLET = new DispatcherServlet();
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String REDIRECT_PREFIX = "redirect:";

    private final ControllerAdapter controllerAdapter;

    private final ViewResolver viewResolver = new ViewResolver();

    private DispatcherServlet() {
        controllerAdapter = new ControllerAdapter(List.of(
                new UserController(new UserService(new UserRepositoryImpl()))));
    }

    public static DispatcherServlet getInstance() {
        return DISPATCHER_SERVLET;
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        final ModelAndView mv = controllerAdapter.handle(request, response);

        if (mv == null) {
            throw new IllegalArgumentException();
        }
        if (isRedirect(mv)) {
            response.setStatus(HttpStatus.REDIRECT);
            response.addHeader(HeaderType.LOCATION, mv.getViewName());
            return;
        }

        final View view = viewResolver.resolveViewName(mv.getViewName());

        if (view == null) {
            throw new NoSuchViewException();
        }

        view.render(mv.getModel(), request, response);
    }

    private boolean isRedirect(ModelAndView mv) {
        return mv.getViewName().startsWith(REDIRECT_PREFIX) || mv.getHttpStatus() == HttpStatus.REDIRECT;
    }
}
