package com.example.userservice.validation;

import com.example.userservice.db.repo.UserRepo;
import com.example.userservice.source.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserValidate {
    private final UserRepo repo;

    public void checkIsExist(Long userId) {
        repo.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn’t exist with given id"));
    }

    public void validateInputs(UserModel model) {
        if (model.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Validation error or request body is an invalid");
        }
    }
}
