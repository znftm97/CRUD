package crud.noticeboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comment= new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

    private String title;

    private String content;

    private int count; // 조회수

    private String postDate;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getPost().add(this);
    }

    public void setFile(List<File> files){
        this.files = files;
        int size = files.size();
        for(int i=0; i<size; i++) {
            files.get(i).setPost(this);
        }
    }

    //==생성 메서드==//
    public static Post createPost(Member member, String title, String content){
        Post post = new Post();
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm분"));
        post.setPostDate(date);

        return post;
    }

    public static Post createPostWithFile(Member member, String title, String content, List<File> files){
        Post post = new Post();
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);
        post.setFile(files);

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm분"));
        post.setPostDate(date);

        return post;
    }

    //==비즈니스 로직==//
    public void addCount(){
        this.count++;
    }
}

