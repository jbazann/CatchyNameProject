package jbazann.catchyname.server.ui.sheet;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jbazann.catchyname.server.model.CachedProject;
import jbazann.catchyname.server.model.Project;
import jbazann.catchyname.server.persistence.cassandra.CachedProjectRepository;
import jbazann.catchyname.server.persistence.cassandra.ProjectRepository;
import jbazann.catchyname.server.ui.sheet.SheetTrackingService.TrackingMessage;

@Service
public final class SheetStateService {
    
    @Autowired
    private CachedProjectRepository cachedProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SheetTrackingService trackingService;

    private HashMap<UUID,CachedProject> projectCache = new HashMap<>();

    public CachedProject updateState(UUID user, String newData) {
        CachedProject newState = cachedProjectRepository.save(getStateFor(user).update(newData));;
        projectCache.put(user,newState);
        trackingService.send(TrackingMessage.of(newState));
        return newState;
    }

    public CachedProject getStateFor(UUID user) {
        if(projectCache.containsKey(user)) return projectCache.get(user);

        Project latestProject = projectRepository.findFirstByUserOrderByLastEditDesc(user);
        if(latestProject != null) return cacheProject(latestProject);

        return cacheProject(startNewProject(user));
    }

    private Project startNewProject(UUID user) {
        return projectRepository.save(Project.newProject(user, "You're not supposed to be able to see this text."));//that is a lie
    }

    private CachedProject cacheProject(Project project) {
        CachedProject cached = CachedProject.fromProject(project);
        projectCache.put(project.user(), cachedProjectRepository.save(cached));
        return projectCache.get(project.user());
    }

}
