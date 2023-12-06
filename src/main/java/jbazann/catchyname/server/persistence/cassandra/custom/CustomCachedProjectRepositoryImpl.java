package jbazann.catchyname.server.persistence.cassandra.custom;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

import jbazann.catchyname.server.model.CachedProject;
import jbazann.catchyname.server.model.Project;
import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

public class CustomCachedProjectRepositoryImpl implements CustomCachedProjectRepository {
    
    @Autowired
    CassandraOperations cassandra;

    @Override
    public boolean flush(UUID user) {
        CachedProject cached = cassandra.selectOne(query(where("id").is(user)), CachedProject.class);
        if(cached == null) return false; // TODO Exceptions

        Project oldState = cassandra.selectOne(
            query(where("user_id").is(user))
            .and(where("last_edit").is(cached.lastEdit())), Project.class); 
        if(oldState == null) return false; // TODO Exceptions

        Project unpersisted = Project.updateFromCachedProject(oldState, cached);
        return cassandra.batchOps()
            .insert(unpersisted)
            .delete(oldState)
            .delete(cached)
            .execute().wasApplied();
    }

}