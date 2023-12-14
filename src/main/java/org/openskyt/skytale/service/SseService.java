package org.openskyt.skytale.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openskyt.skytale.dto.MessageDto;
import org.openskyt.skytale.dto.SseMessageDto;
import org.openskyt.skytale.models.SseSubscriber;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    private final List<SseSubscriber> emitters = new CopyOnWriteArrayList<>();
    private ChatroomService chatroomService;

    public SseService(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    public void addEmitter(SseSubscriber sseSubscriber) {
        emitters.add(sseSubscriber);
        sseSubscriber.sseEmitter().onCompletion(() -> emitters.remove(sseSubscriber));
        sseSubscriber.sseEmitter().onTimeout(() -> emitters.remove(sseSubscriber));
    }

    public void sendMessageEvent(SseMessageDto sseMessageDto) {
        for (SseSubscriber emitter : emitters) {
            try {
                if (chatroomService.checkIfParticipantExists(emitter.user().getId(), sseMessageDto.idOfChatroom())) {
                    MessageDto messageDto = new MessageDto(sseMessageDto.user().getName(), sseMessageDto.msgText());
                    emitter.sseEmitter().send((new ObjectMapper()).writeValueAsString(messageDto), MediaType.APPLICATION_JSON);
                }
            } catch (IOException e) {
                emitter.sseEmitter().complete();
                emitters.remove(emitter);
            }
        }
    }
}
