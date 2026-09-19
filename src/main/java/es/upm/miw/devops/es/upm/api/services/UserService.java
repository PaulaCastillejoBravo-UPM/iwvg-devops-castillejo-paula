package es.upm.miw.devops.es.upm.api.services;

import es.upm.miw.devops.es.upm.api.infrastructure.data.daos.UserDao;
import es.upm.miw.devops.es.upm.api.infrastructure.data.models.User;
import es.upm.miw.devops.es.upm.api.infrastructure.data.models.UserActiveUpdate;
import es.upm.miw.devops.es.upm.api.resources.dtos.UserUpdateDto;
import es.upm.miw.devops.es.upm.api.services.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void deleteById(UUID id) {
        User user = userDao.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        userDao.deleteById(id);
    }

    public User updateActive(UUID id, Boolean active) {
        User user = userDao.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        userDao.updateActive(id, active);

        return userDao.findById(id);
    }

    public User updateById(UUID id, UserUpdateDto user) {
        User existingUser = userDao.findById(id);

        if (existingUser == null) {
            throw new UserNotFoundException(id);
        }

        userDao.updateById(id, user);

        return userDao.findById(id);
    }

    public List<User> updateActive(List<UserActiveUpdate> users) {
        List<User> updatedUsers = new ArrayList<>();

        for (UserActiveUpdate user : users) {
            User existingUser = userDao.findById(user.getId());

            if (existingUser == null) {
                throw new UserNotFoundException(user.getId());
            }

            userDao.updateActive(user.getId(), user.getActive());

            updatedUsers.add(userDao.findById(user.getId()));
        }

        return updatedUsers;
    }
}
