package com.itkim.entity;

public class Comments {

	@Override
	public String toString() {
		return "Comments [user=" + user + ", commentId=" + commentId + ", likedCount=" + likedCount + ", time=" + time
				+ ", content=" + content + "]";
	}
	private User user;
    private long commentId;
    private long likedCount;
    private long time;
    private String content;
   
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getLikedCount() {
		return likedCount;
	}
	public void setLikedCount(long likedCount) {
		this.likedCount = likedCount;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
    
}