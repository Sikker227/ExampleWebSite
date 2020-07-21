package org.website.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.website.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
