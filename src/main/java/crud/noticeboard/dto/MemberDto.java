package crud.noticeboard.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    @NotEmpty(message = "필수")
    private String name;

    @NotEmpty(message = "필수")
    private String password;

    @NotEmpty(message = "필수")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
}
