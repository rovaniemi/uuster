package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpForm {

    @NotBlank
    @Size(max=35)
    private String firstName;

    @NotBlank
    @Size(max=65)
    private String lastName;

    @NotBlank
    @Size(max=65)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 300)
    private String password;

    @NotBlank
    @Size(min = 6, max = 300)
    private String confirmPassword;

    @AssertTrue(message="Confirm password field should be equal than password field")
    private boolean isValid() {
        return this.password.equals(this.confirmPassword);
    }
}
