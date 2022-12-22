package ru.uuserless.example.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.uuserless.example.userservice.model.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {

}
