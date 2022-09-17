package com.project.questapp.responses;

import com.project.questapp.entities.User;

import lombok.Data;

@Data
public class UserResponse {
	
	Long id;
	int avatarId;
	String userName;
	
	public UserResponse(User entiy) {
		this.id = entiy.getId();
		this.avatarId = entiy.getAvatar();
		this.userName = entiy.getUserName();
	}
}
