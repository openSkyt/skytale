package org.openskyt.skytale.repositories;

import org.openskyt.skytale.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByAuthorId(Long userId);
    List<Message> findAllByChatroomId(Long roomId);
}
