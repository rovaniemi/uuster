package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import uuster.domain.NewsPicture;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsForm {

    @NotBlank
    @Length(min = 20, max = 20000)
    private String text;

    @NotBlank
    @Length(min = 1, max = 500)
    private String title;

    @NotBlank
    @Length(min = 1, max = 500)
    private String lean;

    @NotBlank
    @Length(min = 1, max = 500)
    private String tags;

    @NotEmpty
    private NewsPicture newsPicture;

}
