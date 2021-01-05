package crud.noticeboard.service;

import crud.noticeboard.domain.File;
import crud.noticeboard.domain.Post;
import crud.noticeboard.repository.FileRepository;
import crud.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;

    //파일 저장
    @Transactional
    @Override
    public void saveFile(MultipartFile files[], Long postId) {

        Post findPost = postRepository.findByIdCustom(postId);

        for(int i=0; i<files.length; i++){
            try {
                String origFilename = files[i].getOriginalFilename(); // 원본 파일 명
                String filename = System.currentTimeMillis() + " - " + origFilename; // 파일 이름 중복되지 않도록

                // 실행되는 위치 즉 프로젝트 폴더에 files 폴더에 파일 저장됨
                String savePath = System.getProperty("user.dir") + "\\files";

                //파일 저장되는 폴더 없으면 생성
                if (!new java.io.File(savePath).exists()) {
                    try{
                        new java.io.File(savePath).mkdir();
                    }
                    catch(Exception e){
                        e.getStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                files[i].transferTo(new java.io.File(filePath));

                //파일 생성
                File file = File.createFile(origFilename, filename, filePath, findPost);

                //파일 저장
                fileRepository.save(file);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    } // 파일저장

}
