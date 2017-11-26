package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsForm {

    @NotBlank
    @Length(min = 20, max = 20000)
    private String text;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String title;
}
