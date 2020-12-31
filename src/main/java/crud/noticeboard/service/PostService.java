package crud.noticeboard.service;

import crud.noticeboard.domain.Post;

public interface PostService {

    void createPost(String title, String content);
    void addCount(Post post);
}
