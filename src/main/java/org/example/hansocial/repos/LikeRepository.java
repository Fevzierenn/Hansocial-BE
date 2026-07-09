package org.example.hansocial.repos;

import org.example.hansocial.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

	@Query("SELECT l FROM Like l WHERE " +
			"(:userId IS NULL OR l.user.id = :userId) AND " +
			"(:postId IS NULL OR l.post.id = :postId)")
	List<Like> findByUserIdAndPostIdOptional(
			@Param("userId") Long userId,
			@Param("postId") Long postId
	);

	@Query(value = 	"select 'liked', l.post_id, u.avatar, u.user_name from "
			+ "p_like l left join user u on u.id = l.user_id "
			+ "where l.post_id in :postIds limit 5", nativeQuery = true)
	List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);
}
