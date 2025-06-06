package org.example.hansocial.responses;

import org.example.hansocial.entities.Post;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostResponse {
	
	Long id;
	Long userId;
	String userName;
	String title;
	String text;
	List<LikeResponse> postLikes;
	Date createDate;
	
	public PostResponse(Post entity, List<LikeResponse> likes) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.userName = entity.getUser().getUserName();
		this.title = entity.getTitle();
		this.text = entity.getText();
		this.postLikes = likes;
		this.createDate = entity.getCreateDate();
	}
}
