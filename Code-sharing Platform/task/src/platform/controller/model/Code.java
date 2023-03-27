package platform.controller.model;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Code {
    private String code;

    public Code() {
        this.code = """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello world!");
                    }
                }
                """;
    }
}
