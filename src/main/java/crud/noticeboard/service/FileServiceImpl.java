package crud.noticeboard.service;

import crud.noticeboard.domain.File;
import crud.noticeboard.dto.FileDto;
import crud.noticeboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    //파일 저장
    @Transactional
    @Override
    public Long saveFile(FileDto fileDto) {
        ModelMapper modelMapper = new ModelMapper();
        File file = modelMapper.map(fileDto, File.class);

        File savedFile = fileRepository.save(file);
        return savedFile.getId();
    }

    ///파일 조회
    @Override
    public FileDto getFile(Long id) {
        File file = fileRepository.findById(id).get();

        ModelMapper modelMapper = new ModelMapper();
        FileDto fileDto = modelMapper.map(file, FileDto.class);

        return fileDto;
    }



}
