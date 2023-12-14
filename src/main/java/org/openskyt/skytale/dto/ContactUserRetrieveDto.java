package org.openskyt.skytale.dto;

import org.openskyt.skytale.models.User;

public record ContactUserRetrieveDto(Long userId, String userName) {

    public ContactUserRetrieveDto(User user){
        this(user.getId(), user.getName());
    }



}
