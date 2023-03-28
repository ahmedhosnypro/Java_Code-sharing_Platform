package platform.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import platform.model.Gist;
import platform.service.GistService;

import java.util.List;

@Controller("/")
public class WebController {

    @Autowired
    private GistService gistService;

    //get gist by id
    @GetMapping(path = "/code/{id}", produces = "text/html")
    public String getCodeById(Model model, @PathVariable int id) {
        Gist gist = gistService.getGist(id);
        model.addAttribute("code", gist.getCode());
        model.addAttribute("date", gist.getDate());
        return "code";
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public String getNewCode() {
        return "new";
    }

    @GetMapping(path = "/code/latest", produces = "text/html")
    public String getLatestCode(Model model) {
        List<Gist> gists = gistService.getLatest(10);
        model.addAttribute("gists", gists);
        return "latest";
    }
}
