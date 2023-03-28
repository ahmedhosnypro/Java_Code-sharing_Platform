package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "gist")
public class Gist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;
    UUID uuid = UUID.randomUUID();
    private String code;
    private String creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gist_restriction_id", referencedColumnName = "id", nullable = false)
    private GistRestriction gistRestriction;

    public Gist(Gist gist) {
        this.id = gist.id;
        this.uuid = UUID.fromString(gist.uuid.toString());
        this.code = gist.code;
        this.creationDate = gist.creationDate;
        this.gistRestriction = gist.gistRestriction;
    }

    public int getTime() {
        if (gistRestriction.getDeleteDate() != null) {
            return (int) ((Long.parseLong(gistRestriction.getDeleteDate()) - System.currentTimeMillis()) / 1000);
        }
        return 0;
    }


    public void setAvailableSeconds(int seconds) {
        if (seconds > 0) {
            if (gistRestriction == null) {
                gistRestriction = new GistRestriction();
            }
            gistRestriction.setDeleteDate(creationDate, seconds);
            gistRestriction.setTimeLimited(true);
        }
    }

    public void setViews(int views, boolean isUpdate) {
        if (gistRestriction == null) {
            gistRestriction = new GistRestriction();
        }
        gistRestriction.setViews(views, isUpdate);
        if (!isUpdate && views > 0) {
            gistRestriction.setViewsLimited(true);
        }
    }

    public Integer getViews() {
        if (gistRestriction.getViews() == null)
            return null;
        return gistRestriction.getViews();
    }

    public boolean isExpired() {
        return (gistRestriction.getDeleteDate() != null &&
                (System.currentTimeMillis() > Long.parseLong(gistRestriction.getDeleteDate())) ||
                (gistRestriction.getViews() != null && gistRestriction.getViews() == 0));
    }
}
