package was.spring.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import board.mvc.controller.UserController;
import board.mvc.repository.UserRepositoryImpl;
import board.mvc.service.UserService;
import was.request.HttpRequest;
import was.response.HttpResponse;

import java.util.List;

public class DispatcherServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    private boolean isInit = false;

    private ControllerAdapter controllerAdapter;

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

        controllerAdapter.handle(request, response);
    }

    public boolean isInit() {
        return isInit;
    }
}
