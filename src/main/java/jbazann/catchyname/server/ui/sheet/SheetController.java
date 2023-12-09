package jbazann.catchyname.server.ui.sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class SheetController {

    @Autowired
    private SheetStateService sheetState;

    @PostMapping(value = "/sheet/sync", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<HttpStatus> sync(@RequestBody SheetTrackingService.TrackingMessage data) {
        sheetState.updateState(data.user(),data.content());
        return Mono.just(HttpStatus.I_AM_A_TEAPOT);
    }
}
