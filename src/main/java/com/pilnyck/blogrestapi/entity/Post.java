package com.pilnyck.blogrestapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    private boolean star;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Tag> tags = new LinkedHashSet<>();

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return postId == post.postId && star == post.star && title.equals(post.title) && content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, title, content, star);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", comments=" + comments +
                ", tags=" + tags +
                '}';
    }
}
