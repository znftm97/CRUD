package crud.noticeboard.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import crud.noticeboard.dto.PostMemberSearchDto;
import crud.noticeboard.dto.PostSearchCondition;
import crud.noticeboard.dto.QPostMemberSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static crud.noticeboard.domain.QMember.member;
import static crud.noticeboard.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostMemberSearchDto> search(PostSearchCondition postSearchCondition, Pageable pageable) {
        QueryResults<PostMemberSearchDto> results = queryFactory
                .select(new QPostMemberSearchDto(
                        post.id,
                        member.name,
                        post.title,
                        post.postDate,
                        post.count))
                .from(post)
                .leftJoin(post.member, member)
                .where(
                        usernameEq(postSearchCondition.getName()),
                        titleEq(postSearchCondition.getTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostMemberSearchDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameEq(String usernameParam){
        return usernameParam != null ? member.name.eq(usernameParam) : null;
    }

    private BooleanExpression titleEq(String titleParam){
        return titleParam != null ? post.title.eq(titleParam) : null;
    }
}
