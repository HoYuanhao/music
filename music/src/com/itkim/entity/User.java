package com.itkim.entity;

public class User {

    @Override
	public String toString() {
		return "User [nickname=" + nickname + ", avatarUrl=" + avatarUrl + "]";
	}
	private String nickname;
    private String avatarUrl;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}