package platform.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GistRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private boolean timeLimited;
    private String deleteDate;
    private boolean viewsLimited;
    private Integer views;

    void setDeleteDate(String creationDate, int seconds) {
        deleteDate = String.valueOf(Long.parseLong(creationDate) + seconds * 1000L);
    }

    void setViews(int viewsLimit, boolean isUpdate) {
        if (isUpdate) {
            views = viewsLimit;
            return;
        }
        if (viewsLimit > 0) {
            this.views = viewsLimit;
        }
    }
}
