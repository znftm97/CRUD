package crud.noticeboard.repository;

import crud.noticeboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.id = :id")
    Member findByIdCustom(@Param("id") Long id);
    Member findByName(String username);
}
