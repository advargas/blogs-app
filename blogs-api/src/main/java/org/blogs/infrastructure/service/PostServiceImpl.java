package org.blogs.infrastructure.service;

import org.blogs.domain.model.Post;
import org.blogs.domain.service.PostService;
import org.blogs.infrastructure.entities.PostEntity;
import org.blogs.infrastructure.repository.DataPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final DataPostRepository postRepository;

    @Autowired
    public PostServiceImpl(DataPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getByUser(String user) {
        List<PostEntity> result = this.postRepository.findByUser(user);
        List<Post> posts = new ArrayList<>();

        for (PostEntity entity : result) {
            posts.add(entity.toPost());
        }
        return posts;
    }

    public Post addPost(Post post, String user) {
        this.postRepository.save(new PostEntity(post.getId(), user, post.getTitle(), post.getContent(),
                post.getImage()));
        return post;
    }

    public Optional<Post> updatePost(Post post) {
        Optional<PostEntity> result = this.postRepository.findById(post.getId());

        if (result.isPresent()) {
            PostEntity oldPost = result.get();
            oldPost.setTitle(post.getTitle());
            oldPost.setImage(post.getImage());
            oldPost.setContent(post.getContent());
            this.postRepository.save(oldPost);
            return Optional.of(post);
        }
        return Optional.empty();
    }

    public Optional<Post> deletePost(UUID id) {
        Optional<PostEntity> result = this.postRepository.findById(id);

        if (result.isPresent()) {
            this.postRepository.delete(result.get());
            return Optional.of(result.get().toPost());
        }
        return Optional.empty();
    }
}
