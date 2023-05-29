package board.mvc.controller.dto;

import board.mvc.domain.User;

public class UserSessionDto {
    private final Long id;
    private final String username;

    public UserSessionDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static UserSessionDto from(User user) {
        return new UserSessionDto(user.getId(), user.getName());
    }
}
