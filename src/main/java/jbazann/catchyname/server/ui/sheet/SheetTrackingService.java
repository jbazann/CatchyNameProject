package jbazann.catchyname.server.ui.sheet;

import java.util.UUID;

import org.springframework.stereotype.Service;

import jbazann.catchyname.server.model.CachedProject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.publisher.Sinks.Many;

@Service
public class SheetTrackingService {

    private final Many<TrackingMessage> sink = Sinks.many().multicast().directAllOrNothing(); 

    public record TrackingMessage(String tag, String content, UUID user) {
        public static TrackingMessage of(CachedProject p) {
            return new TrackingMessage("I don't know why I added tags",p.data(),p.user());
        }
    }

    public String send(TrackingMessage data) {
        sink.emitNext(data,EmitFailureHandler.FAIL_FAST);
        return "i don't know what this means right now"; // TODO dw i'll fix this soon
    }

    public Flux<TrackingMessage> getFlux() {
        return sink.asFlux();
    }
    
}
