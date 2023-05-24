package board.mvc.controller.dto;

import board.mvc.domain.User;

public class UserJoinDto {
    private String name;
    private String password;

    public UserJoinDto() {
    }

    public UserJoinDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toUser() {
        return new User(name, password);
    }
}
