package com.project.questapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdateRequest;
import com.project.questapp.responses.CommentResponse;
import com.project.questapp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping
	public List<CommentResponse> getAllComment(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
		return commentService.getAllCommnetsWitParam(userId, postId);
	}
	
	@GetMapping("/{commentId}")
	public Comment getOneComment(@PathVariable Long commentId) {
		return commentService.getOneCommentById(commentId); 
	}
	
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest newComment) {
		return commentService.createOneComment(newComment);
	}
	
	@PutMapping("/{commendId}")
	public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest updateComment) {
		return commentService.updateOneCommentById(commentId, updateComment);
	}
	
	@DeleteMapping("/{commendId}")
	public void deleteOeComment(@PathVariable Long commentId) {
		commentService.deleteOneCommentById(commentId);
	}
	
	
}
