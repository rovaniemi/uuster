package uuster.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfilePicture extends AbstractPersistable<Long> {

    @OneToOne
    @JoinColumn
    private Author author;

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String contentType;

    private Long contentLength;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @Override
    public String toString() {
        return "";
    }
}
