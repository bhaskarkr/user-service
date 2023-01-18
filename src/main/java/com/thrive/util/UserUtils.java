package com.thrive.util;

import com.thrive.model.dao.StoredUser;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import io.appform.dropwizard.discovery.bundle.id.IdGenerator;

public interface UserUtils {
    static StoredUser toDao(UserCreateRequest request) {
        return StoredUser.builder()
                .id(IdGenerator.generate("U").getId())
                .active(true)
                .fullName(request.getFullName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
    }

    static User toDto(StoredUser storedUser) {
        return User.builder()
                .id(storedUser.getId())
                .name(storedUser.getFullName())
                .email(storedUser.getEmail())
                .createdAt(storedUser.getCreatedAt())
                .updatedAt(storedUser.getUpdatedAt())
                .build();
    }

}
