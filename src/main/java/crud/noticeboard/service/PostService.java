package crud.noticeboard.service;

import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.PostCreateDto;

public interface PostService {

    Long createPost(String title, String content);
    void addCount(Post post);
    void updatePost(Long postId, PostCreateDto postCreateDto);
    void removePost(Long postId);
}
