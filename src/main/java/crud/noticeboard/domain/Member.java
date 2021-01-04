package crud.noticeboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> post;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comment;

    private String name;

    private String password;

    private String email;

    private String role = "USER";

}
