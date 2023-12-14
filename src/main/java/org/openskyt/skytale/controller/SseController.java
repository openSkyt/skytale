package org.openskyt.skytale.controller;

import org.openskyt.skytale.models.SseSubscriber;
import org.openskyt.skytale.security.SecurityService;
import org.openskyt.skytale.service.SseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {
    private final SseService sseService;
    private SecurityService securityService;

    public SseController(SseService sseService, SecurityService securityService) {
        this.sseService = sseService;
        this.securityService = securityService;
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        SseSubscriber sseSubscriber = new SseSubscriber(emitter, securityService.getLoggedInUser());
        sseService.addEmitter(sseSubscriber);
        return emitter;
    }
}