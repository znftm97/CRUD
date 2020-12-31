package crud.noticeboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    private String title;

    private String content;

    private int count; // 조회수

    private LocalDateTime postDate;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getPost().add(this);
    }

    //==생성 메서드==//
    public static Post createPost(Member member, String title, String content){
        Post post = new Post();
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);
        post.setPostDate(LocalDateTime.now());

        return post;
    }

    //==비즈니스 로직==//
    public void countAdd(){
        this.count = this.count++;
    }
}

