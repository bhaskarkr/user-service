package com.thrive.util;

import com.thrive.model.dao.StoredUser;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface UserUtils {
    static StoredUser toDao(UserCreateRequest request) {
        return StoredUser.builder()
                .id(IdGenerator.generate("B").getId())
                .active(true)
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .ssn(request.getSsn())
                .build();
    }

    static User toDto(StoredUser storedUser) {
        return User.builder()
                .id(storedUser.getId())
                .name(storedUser.getName())
                .usn(storedUser.getSsn())
                .phoneNumber(storedUser.getPhoneNumber())
                .createdAt(storedUser.getCreatedAt())
                .updatedAt(storedUser.getUpdatedAt())
                .build();
    }

}
