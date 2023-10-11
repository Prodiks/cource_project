package cource_project.service;

import cource_project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cource_project.models.User;
import cource_project.models.UserAction;
import cource_project.repository.UserActions;

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
