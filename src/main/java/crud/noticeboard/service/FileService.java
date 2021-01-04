package crud.noticeboard.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveFile(MultipartFile files[], Long postId);
}
