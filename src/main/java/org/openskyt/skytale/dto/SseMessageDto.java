package org.openskyt.skytale.dto;

import org.openskyt.skytale.models.User;

public record SseMessageDto(User user, String msgText, Long idOfChatroom) {
}
