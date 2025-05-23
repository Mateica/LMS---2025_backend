package main.dto;

import java.util.ArrayList;
import java.util.List;
import main.model.ForumUser;
import main.model.Topic;

public class ForumDTO {

	private Long id;
	private boolean visible;
	private List<TopicDTO> topics = new ArrayList<TopicDTO>();
	private List<ForumUserDTO> forumUsers = new ArrayList<ForumUserDTO>();
	private Boolean active;

	public ForumDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForumDTO(Long id, boolean visible, List<TopicDTO> topics, List<ForumUserDTO> forumUsers, Boolean active) {
		super();
		this.id = id;
		this.visible = visible;
		this.topics = topics;
		this.forumUsers = forumUsers;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<TopicDTO> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicDTO> topics) {
		this.topics = topics;
	}

	public List<ForumUserDTO> getForumUsers() {
		return forumUsers;
	}

	public void setForumUsers(List<ForumUserDTO> forumUsers) {
		this.forumUsers = forumUsers;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	

}