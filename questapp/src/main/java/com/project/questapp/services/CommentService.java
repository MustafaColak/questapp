package com.project.questapp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private UserService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<CommentResponse> getAllCommnetsWitParam(Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
		}else if(userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			comments = commentRepository.findByPostId(postId.get());
		}else {
			comments = commentRepository.findAll();
		}
		
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}

	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest newComment) {
		User user = userService.getOneUserById(newComment.getUserId());
		Post post = postService.getOnePostById(newComment.getPostId());
		if(user != null && post != null) {
			Comment commentToSave =  new Comment();
			commentToSave.setId(newComment.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(newComment.getText());
			commentToSave.setCreateDate(new Date());
			return commentRepository.save(commentToSave);
		}else {
			return null;
		}
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest updateComment) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(updateComment.getText());
			return commentRepository.save(commentToUpdate);
		}else {
			return null;
		}
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);		
	}
}
