package org.openskyt.skytale.repositories;

import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
