package board.mvc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import board.mvc.domain.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final Map<Long, User> users = new ConcurrentHashMap<>();

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

    @Override
    public void save(User user) {
        final long id = ID_GENERATOR.incrementAndGet();
        user.setId(id);

        LOGGER.info("USER SAVE: " + user.getId() + ", " + user.getName() + ", " + user.getPassword());

        users.put(id, user);
    }
}
