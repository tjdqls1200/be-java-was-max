package board.mvc.controller;

import board.mvc.controller.dto.UserJoinDto;
import board.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.common.HttpMethod;
import was.spring.servlet.mvc.controller.Controller;
import was.spring.servlet.mvc.controller.ModelAttribute;
import was.spring.servlet.mvc.controller.RequestMapping;

public class UserController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(url = "/users", method = HttpMethod.POST)
    public void join(@ModelAttribute UserJoinDto userJoinDto) {
        LOGGER.info("User Join Start");
        userService.join(userJoinDto);
    }

    @RequestMapping(url = "/users", method = HttpMethod.GET)
    public void readUsers() {
    }
}
