package com.sankha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sankha.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
