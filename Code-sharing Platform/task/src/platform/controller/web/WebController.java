package platform.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import platform.model.CodeGist;

@Controller("/")
public class WebController {

    @GetMapping(path = "/code", produces = "text/html")
    public String getCode(Model model) {
        model.addAttribute("code", CodeGist.getInstance().getCode());
        model.addAttribute("date", CodeGist.getInstance().getDate());
        return "code";
    }

    @GetMapping(path = "/code/new", produces = "text/html")
    public String getNewCode() {
        return "new";
    }
}
