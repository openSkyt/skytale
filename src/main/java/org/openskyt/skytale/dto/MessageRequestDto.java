package org.openskyt.skytale.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDto {

    private Long authorId;
    private Long roomId;
    private String message;
}
