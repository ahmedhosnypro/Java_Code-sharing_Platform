package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Gist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    private String code;
    private Instant date;

    @JsonIgnore
    @JsonProperty("time")
    private int accessTime;

    @JsonIgnore
    @JsonProperty("views")
    private int accessCount;

    @JsonIgnore
    UUID uuid;
}
