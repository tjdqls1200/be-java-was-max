package was.container.mvc.controller;

import was.container.mvc.controller.dto.UserJoinDto;
import was.container.mvc.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void join(UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
    }

    public void readUsers() {

    }

    public void readUser() {
    }
}
