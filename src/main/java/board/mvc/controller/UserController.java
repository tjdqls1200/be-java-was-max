package board.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import board.mvc.controller.dto.UserJoinDto;
import was.spring.servlet.mvc.controller.RequestMapping;
import board.mvc.service.UserService;
import was.spring.servlet.mvc.controller.Controller;

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
