package loananhshop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String name;
    private String usernameOrEmail;
    private String password;
    private String photoUrl;
    private String provider;

}
