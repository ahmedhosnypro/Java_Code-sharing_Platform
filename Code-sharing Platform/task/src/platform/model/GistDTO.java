package platform.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GistDTO {
    private String code;

    @JsonProperty("date")
    private String creationDate;

    @JsonProperty("time")
    private int time;

    @JsonProperty("views")
    private int views;

    @JsonIgnore
    private boolean timeLimited;

    @JsonIgnore
    private boolean viewsLimited;

    @Bean
    private static ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    public static GistDTO convertToDto(Gist gist) {
        GistDTO gistDTO = modelMapper().map(gist, GistDTO.class);
        gistDTO.timeLimited = gist.getGistRestriction().isTimeLimited();
        gistDTO.viewsLimited = gist.getGistRestriction().isViewsLimited();
        return gistDTO;
    }

    public static Gist convertToEntity(GistDTO gistDTO) {
        Gist gist = modelMapper().map(gistDTO, Gist.class);

        if (gistDTO.creationDate == null) {
            gist.setCreationDate(String.valueOf(System.currentTimeMillis()));
        }
        gist.setAvailableSeconds(gistDTO.time);
        gist.setViews(gistDTO.getViews(), false);

        return gist;
    }
}