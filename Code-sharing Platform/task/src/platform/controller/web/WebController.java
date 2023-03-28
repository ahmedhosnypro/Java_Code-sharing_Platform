package platform.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.model.GistDTO;
import platform.service.GistService;

@Controller("/")
public class WebController {

    @Autowired
    private GistService gistService;

    @GetMapping(path = "/code/{uuid}", produces = "text/html")
    public Object getCodeById(Model model, @PathVariable String uuid) {
        var gist = gistService.getGist(uuid);
        if (gist == null) {
            return ResponseEntity.notFound().build();
        }
        GistDTO gistDTO = GistDTO.convertToDto(gist);
        model.addAttribute(gistDTO);
        return "code";
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public String getNewCode() {
        return "new";
    }

    @GetMapping(path = "/code/latest", produces = "text/html")
    public String getLatestCode(Model model) {
        var gists = gistService.getLatest(10);
        model.addAttribute("gists", gists);
        return "latest";
    }
}
