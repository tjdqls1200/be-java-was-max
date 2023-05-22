package was.container.mvc.controller;

import was.common.HttpMethod;
import was.container.mvc.controller.dto.UserJoinDto;
import was.container.mvc.controller.proxy.RequestMapping;
import was.container.mvc.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(url = "/users", method = HttpMethod.POST)
    public void join(UserJoinDto userJoinDto) {
        userService.join(userJoinDto);
    }

    @RequestMapping(url = "/users", method = HttpMethod.GET)
    public void readUsers() {
    }

    public void readUser() {
    }
}
