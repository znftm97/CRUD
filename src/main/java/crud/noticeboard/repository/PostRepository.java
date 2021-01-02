package crud.noticeboard.repository;

import crud.noticeboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("select p from Post p where p.id = :id")
    Post findByIdCustom(@Param("id") Long id);
}
