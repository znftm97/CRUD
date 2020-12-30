package crud.noticeboard.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
}
