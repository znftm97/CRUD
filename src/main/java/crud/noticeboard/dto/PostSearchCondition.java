package crud.noticeboard.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

    private String name;
    private String title;
    private String searchWord;
    private String searchOption;
}
