package was.container.mvc.controller.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.container.FrontServlet;
import was.container.RequestMapping;
import was.container.UserRequestMapping;
import was.container.mvc.controller.UserController;
import was.container.mvc.controller.dto.UserJoinDto;
import was.container.mvc.repository.UserRepositoryImpl;
import was.container.mvc.service.UserService;
import was.request.HttpRequest;
import was.response.HttpResponse;

public class UserControllerProxy implements ControllerProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerProxy.class);

    private final UserController userController = new UserController(new UserService(new UserRepositoryImpl()));

    @Override
    public void process(HttpRequest request, HttpResponse response, RequestMapping requestMapping) {
        //TODO HttpRequest -> HttpServletRequest -> getAttribute()

        //임시
        if (requestMapping == UserRequestMapping.JOIN) {
            LOGGER.info("CONTROLLER PROXY : USER JOIN START");
            final UserJoinDto userJoinDto = new UserJoinDto(request.getAttribute("name").get(), request.getAttribute("password").get());

            userController.join(userJoinDto);
            LOGGER.info("CONTROLLER PROXY : USER JOIN END");
        }
        if (requestMapping == UserRequestMapping.READ_USER) {
            userController.readUser();
        }
        if (requestMapping == UserRequestMapping.READ_USERS) {
            userController.readUsers();
        }
    }
}
