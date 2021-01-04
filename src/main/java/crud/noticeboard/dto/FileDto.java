package crud.noticeboard.dto;

import lombok.Data;

@Data
public class FileDto {

    private Long id;
    private String originFilename;
    private String filename;
    private String filePath;
}
