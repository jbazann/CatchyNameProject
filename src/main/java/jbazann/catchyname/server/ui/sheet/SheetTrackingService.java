package jbazann.catchyname.server.ui.sheet;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jbazann.catchyname.server.ui.sheet.SheetController.SheetSyncData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.publisher.Sinks.Many;

@Service
public class SheetTrackingService {

    private static final UUID testuser = UUID.fromString("19443e46-058e-4b7b-b0e7-fbdb5b1d2c66");

    @Autowired
    private SheetStateService stateService;
    private final Many<String> sink = Sinks.many().multicast().directAllOrNothing(); 

    public String validateAndApply(ArrayList<SheetSyncData> data) {
        String target = stateService.getStateFor(testuser);
        for(SheetSyncData op : data) {
            target = SheetOperations.parse(op.operation())
                .apply(op.content(), target, op.metadata());
        }
        sink.emitNext(stateService.updateState(testuser, target),EmitFailureHandler.FAIL_FAST);
        return "i don't know what this means right now"; // TODO dw i'll fix this soon
    }

    public Flux<String> getFlux() {
        return sink.asFlux();
    }
    
}
