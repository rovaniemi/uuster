package uuster.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author extends AbstractPersistable<Long> {

    private boolean admin;

    @NotBlank
    @Length(min = 1, max = 35)
    private String firstName;

    @NotBlank
    @Length(min = 1, max = 65)
    private String lastName;

    @NotBlank
    @Length(min = 1, max = 65)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 6, max = 300)
    private String password;

    private Picture picture;

    @ManyToMany
    private List<News> news;
}
