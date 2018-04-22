package com.itkim.entity;

import java.util.List;

public class CommentBean {
    private long userId;
    private List<Comments> comments;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
}