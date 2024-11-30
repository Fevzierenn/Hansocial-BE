package org.example.hansocial.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {
	
	Long id;
	Long userId;
	Long postId;
	
}
