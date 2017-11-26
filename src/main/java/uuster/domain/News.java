package uuster.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News extends AbstractPersistable<Long> {

    @OneToMany(mappedBy = "news", fetch = FetchType.EAGER)
    private List<NewsPicture> pictures;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String title;

    @NotBlank
    @Length(min = 20, max = 20000)
    private String text;

    private LocalDate time;

    @ManyToMany
    private List<Author> authors;
}
