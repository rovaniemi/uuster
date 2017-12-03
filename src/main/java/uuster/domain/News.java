package uuster.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News extends AbstractPersistable<Long> {

    @NotBlank
    @Length(min = 1, max = 1000)
    private String title;

    @NotBlank
    @Length(min = 20, max = 20000)
    private String lead;

    @NotBlank
    @Length(min = 20, max = 20000)
    private String text;

    private LocalDate time;

    @ManyToMany
    private List<Author> authors;

    @ManyToMany
    private Set<Tag> tags;

    @OneToMany(mappedBy = "news", fetch = FetchType.EAGER)
    private List<NewsPicture> pictures;

    public News(String title, String text, String lead, Set<Tag> tags) {
        this.title = title;
        this.text = text;
        this.lead = lead;
        this.authors = new ArrayList<>();
        this.tags = tags;
        this.pictures = new ArrayList<>();
        this.time = LocalDate.now();
    }
}
