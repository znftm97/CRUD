package crud.noticeboard.repository;

import crud.noticeboard.dto.PostSearchCondition;
import crud.noticeboard.dto.PostMemberSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostMemberSearchDto> search(PostSearchCondition postSearchCondition, Pageable pageable);
}
