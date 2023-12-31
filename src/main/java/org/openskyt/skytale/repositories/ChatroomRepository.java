package org.openskyt.skytale.repositories;

import org.openskyt.skytale.models.Chatroom;
import org.openskyt.skytale.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    Optional<Chatroom> findByName(String name);
    List<Chatroom> findAllByParticipantsId(Long id);
    boolean existsByIdAndParticipants_Id(Long chatroomId, Long participantId);
}
