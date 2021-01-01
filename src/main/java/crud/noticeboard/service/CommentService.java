package crud.noticeboard.service;

public interface CommentService {
    void createComment(String text, Long postId);
    void readComment();
}
