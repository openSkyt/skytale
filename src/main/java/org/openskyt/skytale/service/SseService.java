package org.openskyt.skytale.service;

import org.openskyt.skytale.models.Message;
import org.openskyt.skytale.repositories.MessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final MessageRepository messageRepository;

    public SseService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(messageRepository.findAll().stream()
                        .map(Message::getText)
                        .collect(Collectors.joining(", ")));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
