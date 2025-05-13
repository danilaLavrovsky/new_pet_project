package new_pet_project.repository;

import new_pet_project.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Integer id);

    void deleteByUsername(String username);
}
