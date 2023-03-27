package platform.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class CodeGist {
    private String code;
    private Instant date;

    @JsonIgnore
    private static CodeGist instance;

    private CodeGist() {
        this.code = """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello world!");
                    }
                }
                """;
        this.date = Instant.now();
    }

    @JsonIgnore
    public static CodeGist getInstance() {
        if (instance == null) {
            instance = new CodeGist();
        }
        return instance;
    }

    public static void update(CodeGist codeGist) {
        instance = codeGist;
        instance.date = Instant.now();
    }
}
