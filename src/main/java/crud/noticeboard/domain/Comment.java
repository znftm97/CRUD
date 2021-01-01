package crud.noticeboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String text;

    private String commentDate;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getComment().add(this);
    }

    public void setPost(Post post){
        this.post = post;
        post.getComment().add(this);
    }

    //==생성 메서드==//
    public static Comment createComment(Member member, Post post, String text){
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setText(text);

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm"));
        comment.setCommentDate(date);

        return comment;
    }
}
