package jbazann.catchyname.server.ui.sheet;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jbazann.catchyname.server.model.CachedProject;
import jbazann.catchyname.server.model.Project;
import jbazann.catchyname.server.persistence.cassandra.CachedProjectRepository;
import jbazann.catchyname.server.persistence.cassandra.ProjectRepository;

@Service
public final class SheetStateService {
    
    @Autowired
    private CachedProjectRepository cachedProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private HashMap<UUID,CachedProject> projectCache = new HashMap<>();

    public String updateState(UUID user, String newData) {
        if(!projectCache.containsKey(user)) return "u messed up buddy boy";//TODO Exceptions
        CachedProject newState = projectCache.get(user).update(newData);
        cachedProjectRepository.save(newState);
        projectCache.put(user,newState);
        return newState.data();
    }

    public String getStateFor(UUID user) {
        if(projectCache.containsKey(user)) return projectCache.get(user).data();

        Project latestProject = projectRepository.findFirstByUserOrderByLastEditDesc(user);
        if(latestProject != null) return cacheProject(latestProject).data();

        return cacheProject(startNewProject(user)).data();
    }

    public Project startNewProject(UUID user) {
        return projectRepository.save(Project.newProject(user, "You're not supposed to be able to see this text."));//that is a lie
    }

    public CachedProject cacheProject(Project project) {
        CachedProject cached = CachedProject.fromProject(project);
        projectCache.put(project.user(), cachedProjectRepository.save(cached));
        return projectCache.get(project.user());
    }

}
