package ru.roman.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.models.UserAction;
import ru.roman.courseproject.repository.UserActions;
import ru.roman.courseproject.repository.UsersRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserActionsService {
    private final UserActions userActions;

    private final UsersRepository usersRepository;

    @Autowired
    public UserActionsService(UserActions userActions, UsersRepository usersRepository) {
        this.userActions = userActions;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void writeLog(String action){
        UserAction uAction = new UserAction();

        Optional<User> user = usersRepository.findByEmail(getCurrentUsername());

        user.ifPresent(uAction::setUser);

        uAction.setDateActions(new Timestamp(System.currentTimeMillis()));
        uAction.setDescriptions(action);

        userActions.save(uAction);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
