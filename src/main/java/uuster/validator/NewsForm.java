package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

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

    @NotBlank
    @Length(min = 20, max = 20000)
    private String lead;

    @NotBlank
    @Length(min = 1, max = 500)
    private String tags;

    @NotNull
    private MultipartFile file;
}
