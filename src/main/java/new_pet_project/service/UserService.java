package new_pet_project.service;

import jakarta.transaction.Transactional;
import new_pet_project.DTO.UserCreatForm;
import new_pet_project.entity.Task;
import new_pet_project.entity.User;
import new_pet_project.exception.DataBaseNotFoundException;
import new_pet_project.exception.NameBusyException;
import new_pet_project.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import new_pet_project.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void addUser(
            UserCreatForm userCreatForm
    ) throws NameBusyException {
        if (userRepository.findByUsername(userCreatForm.getUsername()).isPresent()) {
            throw new NameBusyException("Имя пользователя уже занято");
        }
        if (userRepository.findByEmail(userCreatForm.getEmail()).isPresent()) {
            throw new NameBusyException("Почта уже зарегистрирована");
        }
        userCreatForm.setPassword(passwordEncoder.encode(userCreatForm.getPassword()));
        userRepository.save(Mapper.formToUser(userCreatForm));
    }

    @Transactional
    public void updateUser(
            Integer id,
            UserCreatForm userCreatForm
    ) throws NameBusyException {
        if (userRepository.findByUsername(userCreatForm.getUsername()).isPresent() && !(userRepository.findByUsername(userCreatForm.getUsername()).get().getId().equals(id))) {
            throw new NameBusyException("Имя пользователя уже занято");
        }
        if (userRepository.findByEmail(userCreatForm.getEmail()).isPresent() && !(userRepository.findByEmail(userCreatForm.getEmail()).get().getId().equals(id))) {
            throw new NameBusyException("Почта уже зарегистрирована");
        }
        User user = userRepository.findById(id).get();
        user.setUsername(userCreatForm.getUsername());
        user.setEmail(userCreatForm.getEmail());
        user.setPassword(passwordEncoder.encode(userCreatForm.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(
            String username
    ) {
        userRepository.deleteByUsername(username);
    }
}
