package org.example.hansocial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Date;


@Entity
@Table(name="post")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	User user;
	
	String title;
	@Lob
    @Column(columnDefinition="text")
	String text;

	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;


}
