package org.example.hansocial.repos;

import  org.example.hansocial.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

}
