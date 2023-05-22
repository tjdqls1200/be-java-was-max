package was.container.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import was.container.mvc.controller.dto.UserJoinDto;
import was.container.annotation.RequestMapping;
import was.container.mvc.service.UserService;

public class UserController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(url = "/users", method = HttpMethod.POST)
    public void join(UserJoinDto userJoinDto) {
        LOGGER.info("User Join Start");
        userService.join(userJoinDto);
    }

    @RequestMapping(url = "/users", method = HttpMethod.GET)
    public void readUsers() {
    }
}
