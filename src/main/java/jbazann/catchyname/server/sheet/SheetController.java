package jbazann.catchyname.server.sheet;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class SheetController {

    public record SheetSyncData(
        String content, 
        String operation, 
        ArrayList<Integer> metadata
        ) {}

    @Autowired
    private SheetTrackingService sheetTracker;
    
    @PostMapping(value = "/sheet/sync", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> sync(@RequestBody ArrayList<SheetSyncData> data) {

        return Mono.just(sheetTracker.validateAndApply(data));

    }
}
