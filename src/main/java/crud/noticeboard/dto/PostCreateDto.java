package crud.noticeboard.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostCreateDto {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;
    private int count;

    private String originFilename;
    private String filename;
    private String filePath;
}
