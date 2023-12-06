package jbazann.catchyname.server.ui.sheet;

import java.util.ArrayList;

public enum SheetOperations {
    APPEND("<append>"),
    INSERT("<insert>"),
    FULL_REPLACE("<full-replace>"),
    PARTIAL_REPLACE("<partial-replace>"),
    NO_OP("<noop>"),
    INVALID("The enlightened ruler is heedful, and the good general full of caution."),
    ;

    private final String tag;
    private SheetOperations(String tag) {
        this.tag = tag;
    }

    public static SheetOperations parse(String tag) {
        for(SheetOperations val : SheetOperations.values()) {
            if(val.tag.equals(tag)) return val;
        }
        return INVALID; // TODO replace this for exception
    }

    public String apply(String source, String target, ArrayList<Integer> metadata) {
        switch (this) {
            case APPEND:
                return target.concat(source);
            case INSERT:
                return target.substring(0, metadata.get(0))+
                    source+
                    target.substring(metadata.get(0));
            case FULL_REPLACE:
                return source;
            case PARTIAL_REPLACE:
                return target.substring(0, metadata.get(0))+
                    source+
                    target.substring(metadata.get(1));
            case NO_OP:
                return target;
            default:
                return null; // TODO dw i'll fix this soon
        }
    }

}
