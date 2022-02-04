package com.pilnyck.blogrestapi.dto;

import com.pilnyck.blogrestapi.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagWithoutPostsDto {
    private long tagId;
    private String tagName;
}
