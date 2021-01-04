package crud.noticeboard.repository;

import crud.noticeboard.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select f from File f where f.post.id = :postId")
    List<File> findFileByPostId(@Param("postId") Long postId);
}
