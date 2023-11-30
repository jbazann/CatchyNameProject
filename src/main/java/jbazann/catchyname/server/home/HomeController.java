package jbazann.catchyname.server.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class HomeController {
    
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("mainScript", "js/sheet.js");
        model.addAttribute("indexCss","css/index.css");
        model.addAttribute("topbarCss","css/topbar.css");
        model.addAttribute("sheetCss","css/sheet.css");

        return "index";
    }
}
