package jbazann.catchyname.server.sheet;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

@Repository
public class SheetStateRepository {

    private HashMap<String,String> projectCache = new HashMap<>();

    public String getCurrentState(String user) {
        if(!projectCache.containsKey(user)) projectCache.put(user, "Cute cats!"); // TODO remove this when implementing DB
        return projectCache.get(user);
    }

    public void updateState(String user, String project) {
        projectCache.put(user, project);
    }
    
}
