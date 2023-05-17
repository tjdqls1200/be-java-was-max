package was.container.mvc.controller.dto;

import was.container.mvc.domain.User;

public class UserJoinDto {
    private String name;
    private String password;

    public UserJoinDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User toUser() {
        return new User(name, password);
    }
}
