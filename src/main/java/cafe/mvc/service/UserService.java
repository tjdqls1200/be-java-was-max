package cafe.mvc.service;

import cafe.mvc.controller.dto.UserJoinDto;
import cafe.mvc.controller.dto.UserLoginDto;
import cafe.mvc.controller.dto.UserSessionDto;
import cafe.mvc.domain.User;
import cafe.mvc.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(UserJoinDto userJoinDto) {
        userRepository.save(userJoinDto.toUser());
    }

    public UserSessionDto login(UserLoginDto userLoginDto) {
        final User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(IllegalArgumentException::new);

        if (user.isMatchedPassword(userLoginDto.getPassword())) {
            return UserSessionDto.from(user);
        }

        throw new IllegalArgumentException();
    }
}
