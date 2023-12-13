package org.openskyt.skytale.dto;


public record MessageRequestDto (Long authorId, Long roomId, String message) {

}
