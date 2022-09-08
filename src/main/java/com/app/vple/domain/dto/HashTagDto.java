package com.app.vple.domain.dto;

import com.app.vple.domain.HashTag;
import com.app.vple.domain.Post;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HashTagDto {

    Map<String, Long> hashtags = new HashMap<>();

    public HashTagDto(List<Post> entity) {
        for (Post post : entity) {
            for (HashTag hashTag: post.getHashTags()) {
                if (hashtags.containsKey(hashTag.getHashTag())) {
                    Long cnt = hashtags.get(hashTag.getHashTag());
                    hashtags.put(hashTag.getHashTag(), cnt + 1L);
                }
                else {
                    hashtags.put(hashTag.getHashTag(), 1L);
                }
            }
        }
    }
}
