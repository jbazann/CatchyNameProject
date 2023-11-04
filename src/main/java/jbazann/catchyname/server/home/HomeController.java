package jbazann.catchyname.server.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class HomeController {
    
    @RequestMapping("/")
    public String home() {
        return "index.html";
    }
}
