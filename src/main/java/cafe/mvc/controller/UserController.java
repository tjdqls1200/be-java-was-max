package cafe.mvc.controller;

import cafe.mvc.controller.dto.UserJoinDto;
import cafe.mvc.controller.dto.UserLoginDto;
import cafe.mvc.controller.dto.UserSessionDto;
import cafe.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.enums.HttpMethod;
import was.spring.servlet.common.HttpStatus;
import was.spring.servlet.mvc.controller.Controller;
import was.spring.servlet.mvc.controller.annotation.RequestMapping;
import was.spring.servlet.mvc.controller.annotation.ResponseStatus;
import was.spring.servlet.mvc.view.Model;
import was.spring.servlet.mvc.view.ModelAndView;

public class UserController implements Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(url = "/users/join-form", method = HttpMethod.GET)
    public String joinForm(Model model) {
        return "user/form";
    }

    @RequestMapping(url = "/users", method = HttpMethod.POST)
    public ModelAndView join(UserJoinDto userJoinDto) {
        userService.join(userJoinDto);

        return ModelAndView.builder()
                .viewName("users/login-form")
                .httpStatus(HttpStatus.REDIRECT)
                .build();
    }

    @RequestMapping(url = "/users/login-form", method = HttpMethod.GET)
    public String loginForm() {
        return "user/login";
    }

    @ResponseStatus(status = HttpStatus.REDIRECT)
    @RequestMapping(url = "/users/login", method = HttpMethod.POST)
    public String login(UserLoginDto userLoginDto) {
        final UserSessionDto loginUser = userService.login(userLoginDto);
        LOGGER.info("session: " + loginUser.getId() + ", " + loginUser.getUsername());

        return "/";
    }

    @RequestMapping(url = "/", method = HttpMethod.GET)
    public String home() {
        return "index";
    }


    @RequestMapping(url = "/users", method = HttpMethod.GET)
    public void readUsers() {

    }
}
