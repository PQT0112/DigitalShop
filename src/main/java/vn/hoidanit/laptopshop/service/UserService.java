package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;
import java.util.List;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User handleSaveUser(User user) {

        User pqt = this.userRepository.save(user);
        System.out.println(pqt);
        return pqt;
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }
}
