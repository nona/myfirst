package org.myfirst.repository;

import org.myfirst.domain.Comment;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface CommentRepository  extends GraphRepository<Comment> {

}
