package jbazann.catchyname.server.ui.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jbazann.catchyname.server.ui.sheet.SheetTrackingService;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class AdminSheetUpdateService {
    
    @Autowired
    private SheetTrackingService trackingService;

    public Flux<String> getFlux() {
        return trackingService.getFlux().subscribeOn(Schedulers.boundedElastic());
    }

}
