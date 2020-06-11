package org.blogs.infrastructure.repository;

import org.blogs.infrastructure.entities.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataPostRepository extends CrudRepository<PostEntity, UUID> {

    List<PostEntity> findByUser(String user);
}
