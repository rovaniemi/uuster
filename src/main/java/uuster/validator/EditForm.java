package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditForm {

    @Length(max = 35)
    private String firstName;

    @Length(max = 65)
    private String lastName;

    @Email
    private String email;

    @Length(max = 20000)
    private String description;
}
