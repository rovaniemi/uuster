package uuster.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsPicture extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn
    private News news;

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
}
