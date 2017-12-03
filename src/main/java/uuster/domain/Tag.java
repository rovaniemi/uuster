package uuster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag extends AbstractPersistable<Long> {

    @ManyToMany(mappedBy = "tags")
    private Set<News> news;

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;
}
