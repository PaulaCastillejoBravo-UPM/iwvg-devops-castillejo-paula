package es.upm.miw.devops.es.upm.api.services;

import es.upm.miw.devops.es.upm.api.infrastructure.data.daos.UserDao;
import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(UUID id) {
        User user = userDao.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }

    public List<User> findByBillable(Boolean billable) {
        return userDao.findByBillable(billable);
    }
}
