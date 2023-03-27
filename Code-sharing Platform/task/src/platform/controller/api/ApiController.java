package platform.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.controller.model.Code;

@RestController("/api")
public class ApiController {
    @GetMapping(path = "api/code", produces = "application/json")
    public ResponseEntity<Code> getCode() {
        return new ResponseEntity<>(new Code(), HttpStatus.OK);
    }
}
