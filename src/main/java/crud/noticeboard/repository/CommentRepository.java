package crud.noticeboard.repository;

import crud.noticeboard.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c where c.post.id = :postId", countQuery = "select count(c.id) from Comment c")
    Page<Comment> findByComment(@Param("postId") Long postId, Pageable pageable);

    @Query("select c from Comment c where c.id = :id")
    Comment findByIdCustom(@Param("id") Long id);
}
