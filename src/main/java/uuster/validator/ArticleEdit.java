package uuster.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class    ArticleEdit {

    @Length(min = 20, max = 20000)
    private String text;

    @Length(min = 1, max = 500)
    private String title;

    @Length(min = 1, max = 500)
    private String lead;

    @Length(min = 1, max = 500)
    private String tags;

    private MultipartFile file;
}
