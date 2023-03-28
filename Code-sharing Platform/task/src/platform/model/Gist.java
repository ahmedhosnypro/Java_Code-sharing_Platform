package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Gist {
    @JsonIgnore
    private int id;
    private String code;
    private Instant date;
}
