package crud.noticeboard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Post> post;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String name;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String password;

    private String role;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

}
