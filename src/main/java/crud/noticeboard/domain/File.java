package crud.noticeboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class File {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String originFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    //== 연관관계 메서드 ==//
    public void setPost(Post post){
        this.post = post;
        post.getFiles().add(this);
    }

    //==생성 메서드==//
    public static File createFile(String originFilename, String filename, String filePath, Post post){
        File file = new File();
        file.setOriginFilename(originFilename);
        file.setFilename(filename);
        file.setFilePath(filePath);
        file.setPost(post);

        return file;
    }
}
