package cource_project.service;

import cource_project.models.Apartment;
import cource_project.repository.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cource_project.models.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> findAll(){
        return usersRepository.findAll();
    }

    public User findOne(int id){
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public void save(User user){
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser){
        User userToBeUpdated = usersRepository.findById(id).get();

        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setFullName(userToBeUpdated.getFullName());
        userToBeUpdated.setEmail(userToBeUpdated.getEmail());

        usersRepository.save(userToBeUpdated);
    }

    @Transactional
    public void delete(int id){
        usersRepository.deleteById(id);
    }

    public Optional<User> getUserByUsername(String username){
        return usersRepository.findByEmail(username);
    }

    public List<Apartment> getApartmentsByUserId(int id){
        Optional<User> user = usersRepository.findById(id);
        if(user.isPresent()){
            Hibernate.initialize(user.get().getApartments());
            return user.get().getApartments();
        } else {
            return Collections.emptyList();
        }
    }

}
