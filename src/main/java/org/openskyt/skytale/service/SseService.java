package org.openskyt.skytale.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openskyt.skytale.dto.MessageDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    public void sendMessageEvent(MessageDto messageDto) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send((new ObjectMapper()).writeValueAsString(messageDto), MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
