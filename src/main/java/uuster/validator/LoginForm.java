package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 300)
    private String password;
}
