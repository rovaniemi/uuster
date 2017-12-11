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

    private int counter;

    public News(String title, String text, String lead, Set<Tag> tags) {
        this.title = title;
        this.text = text;
        this.lead = lead;
        this.authors = new ArrayList<>();
        this.tags = tags;
        this.pictures = new ArrayList<>();
        this.time = LocalDate.now();
        this.counter = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        News news = (News) o;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (lead != null ? !lead.equals(news.lead) : news.lead != null) return false;
        if (text != null ? !text.equals(news.text) : news.text != null) return false;
        return (time != null ? !time.equals(news.time) : news.time != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (lead != null ? lead.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "";
    }
}
