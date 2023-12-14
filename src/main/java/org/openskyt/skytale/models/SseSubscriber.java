package org.openskyt.skytale.models;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public record SseSubscriber(SseEmitter sseEmitter, User user) {
}
