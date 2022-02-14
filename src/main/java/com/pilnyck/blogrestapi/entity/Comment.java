package com.pilnyck.blogrestapi.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private long commentId;

    @Column(nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId && text.equals(comment.text) && creationDate.equals(comment.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, text, creationDate);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
