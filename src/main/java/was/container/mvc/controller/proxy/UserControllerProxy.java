package was.container.mvc.controller.proxy;

import was.container.RequestMapping;
import was.container.UserRequestMapping;
import was.container.mvc.controller.UserController;
import was.request.HttpRequest;
import was.response.HttpResponse;

public class UserControllerProxy implements ControllerProxy {
    private final UserController userController = new UserController();

    @Override
    public void process(HttpRequest request, HttpResponse response, RequestMapping requestMapping) {
        if (requestMapping == UserRequestMapping.JOIN) {
            userController.join();
        }
        if (requestMapping == UserRequestMapping.READ_USER) {
            userController.readUser();
        }
        if (requestMapping == UserRequestMapping.READ_USERS) {
            userController.readUsers();
        }
    }
}
