package uuster.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author extends AbstractPersistable<Long> {

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

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<ProfilePicture> pictures;

    @ManyToMany(mappedBy = "authors")
    private List<News> news;

    @ManyToMany
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Author author = (Author) o;

        return username.equals(author.username);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
