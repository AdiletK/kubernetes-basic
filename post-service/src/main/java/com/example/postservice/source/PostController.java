package com.example.postservice.source;

import com.example.postservice.db.entity.Post;
import com.example.postservice.db.repo.PostRepo;
import com.example.postservice.externalService.UserService;
import com.example.postservice.source.model.PostModel;
import com.example.postservice.validation.PostValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepo repo;
    private final PostValidate validate;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostModel post) {
        validate.validateInputs(post);
        Post newPost = new Post();
        newPost.setAuthorId(post.getAuthorId());
        newPost.setText(post.getText());
        newPost.setTopic(post.getTopic());
        Post saved = repo.save(newPost);
        userService.updateUserPostsAmount(saved.getAuthorId(), 1);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        validate.checkIsExist(id);
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        validate.checkIsExist(id);
        Post post = repo.findById(id).get();
        Long authorId = post.getAuthorId();
        repo.deleteById(id);
        userService.updateUserPostsAmount(authorId, -1);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody PostModel model) {
        validate.checkIsExist(id);
        validate.validateInputs(model);
        Post post = repo.findById(id).get();
        post.setText(model.getText());
        post.setTopic(model.getTopic());
        return ResponseEntity.ok(repo.save(post));
    }
}
