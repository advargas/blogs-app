package org.blogs.application.controller;

import org.blogs.application.request.PostRequest;
import org.blogs.application.response.ResponseEnvelop;
import org.blogs.domain.model.Post;
import org.blogs.domain.service.PostService;
import org.blogs.infrastructure.exception.NotFoundException;
import org.blogs.infrastructure.util.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getPostsByUser(Principal principal) {
        return this.postService.getByUser(PrincipalUtil.getUsername(principal));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Post addPost(@Valid @RequestBody final PostRequest postRequest, Principal principal) {

        return this.postService.addPost(new Post(UUID.randomUUID(), postRequest.getTitle(),
                postRequest.getImage(), postRequest.getContent()), PrincipalUtil.getUsername(principal));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Post updatePost(@Valid @RequestBody final Post post, Principal principal) throws NotFoundException {

        Optional<Post> updatedPost = this.postService.updatePost(post);
        if (updatedPost.isPresent()) {
            return updatedPost.get();
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEnvelop<String> deletePost(@PathVariable UUID id) throws NotFoundException {
        Optional<Post> oldPost = this.postService.deletePost(id);
        if (oldPost.isPresent()) {
            return new ResponseEnvelop<String>(true, "Post deleted successfully");
        } else {
            throw new NotFoundException();
        }
    }
}
