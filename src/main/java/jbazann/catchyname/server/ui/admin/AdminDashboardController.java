package jbazann.catchyname.server.ui.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Controller
public class AdminDashboardController {

    @Autowired
    private AdminSheetUpdateService updateService;

    @RequestMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("mainScript", "/../../js/admin-dashboard.js");
        model.addAttribute("indexCss","/../../css/index.css");
        model.addAttribute("topbarCss","/../../css/topbar.css");
        model.addAttribute("sheetCss","/../../css/sheet.css");
        return "index";
    }
    
    @ResponseBody
    @GetMapping(value = "/admin/dashboard/watch-sheet")
    public SseEmitter watch() {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L);
        updateService.getFlux().subscribe(
            data -> {
                try{
                    System.out.println("Trying to send:"); // TODO logs
                    System.out.println(data); // TODO logs
                    emitter.send(SseEmitter.event().data(data));
                }catch(IOException e){
                    System.out.println(e.getLocalizedMessage());// TODO Exceptions
                }
            },
            emitter::completeWithError,
            emitter::complete
        );
        return emitter;
    }
    
}
