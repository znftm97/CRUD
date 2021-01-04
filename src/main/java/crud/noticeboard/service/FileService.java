package crud.noticeboard.service;

import crud.noticeboard.dto.FileDto;

public interface FileService {

    Long saveFile(FileDto fileDto);
    FileDto getFile(Long id);
}
