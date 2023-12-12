package org.openskyt.skytale.repositories;

import org.openskyt.skytale.models.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    Optional<Chatroom> findByName(String name);
}
