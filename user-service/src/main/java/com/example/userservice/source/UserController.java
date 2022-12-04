package com.example.userservice.source;

import com.example.userservice.db.entity.User;
import com.example.userservice.db.repo.UserRepo;
import com.example.userservice.source.model.UserModel;
import com.example.userservice.validation.UserValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepo repo;
    private final UserValidate validate;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserModel user) {
        validate.validateInputs(user);
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        return ResponseEntity.ok(repo.save(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        validate.checkIsExist(id);
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        validate.checkIsExist(id);
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody UserModel model) {
        validate.checkIsExist(id);
        validate.validateInputs(model);
        User user = repo.findById(id).get();
        user.setUsername(model.getUsername());
        return ResponseEntity.ok(repo.save(user));
    }

    @PutMapping("/post_count/{id}")
    public ResponseEntity<?> updatePostAmount(@PathVariable Long id, @RequestParam Integer count) {
        validate.checkIsExist(id);
        User user = repo.findById(id).get();
        int initialCount = user.getPostsAmount() != null ? user.getPostsAmount() : 0;
        user.setPostsAmount(initialCount + count);
        return ResponseEntity.ok(repo.save(user));
    }
}
