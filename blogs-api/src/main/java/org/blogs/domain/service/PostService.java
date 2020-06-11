package org.blogs.domain.service;

import org.blogs.domain.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    List<Post> getByUser(String user);

    Post addPost(Post post, String user);

    Optional<Post> updatePost(Post post);

    Optional<Post> deletePost(UUID id);
}
