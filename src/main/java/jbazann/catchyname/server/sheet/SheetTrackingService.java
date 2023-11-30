package jbazann.catchyname.server.sheet;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jbazann.catchyname.server.sheet.SheetController.SheetSyncData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.publisher.Sinks.Many;

@Service
public class SheetTrackingService {

    @Autowired
    private SheetStateRepository stateRepository;
    private final Many<String> sink = Sinks.many().multicast().directAllOrNothing(); 

    public String validateAndApply(ArrayList<SheetSyncData> data) {
        String target = stateRepository.getCurrentState(null); //TODO dw i'll fix this soon
        for(SheetSyncData op : data) {
            target = SheetOperations.parse(op.operation())
                .apply(op.content(), target, op.metadata());
        }
        stateRepository.updateState(null, target); //TODO dw i'll fix this soon
        sink.emitNext(target,EmitFailureHandler.FAIL_FAST);
        return "i don't know what this means right now"; // TODO dw i'll fix this soon
    }

    public Flux<String> getFlux() {
        return sink.asFlux();
    }
    
}
