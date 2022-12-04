package com.example.postservice.validation;

import com.example.postservice.db.repo.PostRepo;
import com.example.postservice.source.model.PostModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class PostValidate {
    private final PostRepo repo;

    public void checkIsExist(Long userId) {
        repo.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post doesn’t exist with given id"));
    }

    public void validateInputs(PostModel model) {
        if (model.getAuthorId() == null || model.getText().isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Validation error or request body is an invalid");
        }
    }
}
