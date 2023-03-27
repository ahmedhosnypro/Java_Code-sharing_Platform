package platform.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import platform.controller.model.Code;

@Controller("/")
public class WebController {

    @GetMapping(path = "/code", produces = "text/html")
    public String getCode(Model model) {
        model.addAttribute("code", new Code().getCode());
        return "code";
    }
}
