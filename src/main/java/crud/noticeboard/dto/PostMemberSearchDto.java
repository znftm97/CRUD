package crud.noticeboard.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PostMemberSearchDto {

    private Long id;
    private String name;
    private String title;
    private String postDate;
    private int count;

    @QueryProjection
    public PostMemberSearchDto(Long id, String name, String title, String postDate, int count) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.postDate = postDate;
        this.count = count;
    }
}
