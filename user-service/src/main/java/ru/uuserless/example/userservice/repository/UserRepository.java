package ru.uuserless.example.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.uuserless.example.userservice.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findUserByEmail(String email);

}
