package com.colinaardsma.tfbps.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colinaardsma.tfbps.models.Post;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {
    
    List<Post> findAll();
    List<Post> findAllByOrderByCreatedDesc();
	
	List<Post> findByAuthor_uid(int authorId);
    Post findByUid(int uid);
    
}
