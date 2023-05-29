package was.spring.servlet;

import board.mvc.controller.UserController;
import board.mvc.repository.UserRepositoryImpl;
import board.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.request.HttpRequest;
import was.response.HttpResponse;
import was.spring.servlet.http.HttpStatus;
import was.spring.servlet.mvc.view.ModelAndView;
import was.spring.servlet.mvc.view.View;

import java.util.List;

public class DispatcherServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    private boolean isInit = false;

    private ControllerAdapter controllerAdapter;

    private ViewResolver viewResolver = new ViewResolver();

    public DispatcherServlet() {
        init();
    }

    public void init() {
        LOGGER.info("FRONT SERVLET INIT");
        isInit = true;
        //TODO 컨트롤러 초기화
        controllerAdapter = new ControllerAdapter(List.of(
                new UserController(new UserService(new UserRepositoryImpl()))));
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {
        LOGGER.info("FRONT SERVLET SERVICE");

        final ModelAndView mv = controllerAdapter.handle(request, response);

        //TODO refactor
        response.setStatus(mv.getHttpStatus());
        if (mv.getHttpStatus() == HttpStatus.REDIRECT || mv.getViewName().startsWith("redirect:")) {
            response.addHeader("Location", mv.getViewName());
            return;
        }

        final View view = viewResolver.resolveViewName(mv.getViewName());

        //TODO
        if (view == null) {
            return;
        }

        view.render(mv.getModel(), request, response);
    }

    public boolean isInit() {
        return isInit;
    }
}
