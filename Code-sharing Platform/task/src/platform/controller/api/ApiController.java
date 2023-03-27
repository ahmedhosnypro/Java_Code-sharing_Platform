package platform.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import platform.model.CodeGist;

import java.util.Map;

@RestController("/api")
public class ApiController {
    @GetMapping(path = "api/code", produces = "application/json")
    public ResponseEntity<CodeGist> getCode() {
        return new ResponseEntity<>(CodeGist.getInstance(), HttpStatus.OK);
    }

    @PostMapping(path = "api/code/new", produces = "application/json")
    public ResponseEntity<Object> save(@RequestBody CodeGist codeGist) {
        CodeGist.update(codeGist);
        return new ResponseEntity<>(Map.of(), HttpStatus.OK);
    }
}
