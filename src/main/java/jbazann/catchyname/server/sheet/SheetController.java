package jbazann.catchyname.server.sheet;

import java.util.ArrayList;

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
    
    @PostMapping(value = "/sheet/sync", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SheetSyncData> sync(@RequestBody SheetSyncData data) {

        return Mono.just(data);

    }
}
