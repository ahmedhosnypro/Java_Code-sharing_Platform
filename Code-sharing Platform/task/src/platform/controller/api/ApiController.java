package platform.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.model.GistDTO;
import platform.service.GistService;

import java.util.Map;

@RestController("/api")
public class ApiController {
    @Autowired
    private GistService gistService;

    @GetMapping(path = "api/code/{id}", produces = "application/json")
    public ResponseEntity<Object> getGist(@PathVariable String id) {
        var gist = gistService.getGist(id);
        if (gist == null) {
            return new ResponseEntity<>(Map.of("error", "Not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(GistDTO.convertToDto(gist), HttpStatus.OK);
    }

    @GetMapping(path = "api/code/latest", produces = "application/json")
    public ResponseEntity<Object> getLatest() {
        return new ResponseEntity<>(gistService.getLatest(10), HttpStatus.OK);
    }

    @PostMapping(path = "api/code/new", produces = "application/json")
    public ResponseEntity<Object> newGist(@RequestBody GistDTO newGist) {
        var id = String.valueOf(gistService.save(newGist));
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }
}
