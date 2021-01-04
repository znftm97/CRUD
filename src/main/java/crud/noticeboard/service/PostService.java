package crud.noticeboard.service;

import crud.noticeboard.domain.File;
import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.PostCreateDto;

import java.util.List;

public interface PostService {

    void createPost(String title, String content);
    void createPostWithFile(String title, String content, List<File> files);
    void addCount(Post post);
    void updatePost(Long postId, PostCreateDto postCreateDto);
    void removePost(Long postId);
}
