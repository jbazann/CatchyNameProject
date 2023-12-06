package jbazann.catchyname.server.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.MapIdentifiable;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("cached_projects")
public record CachedProject(
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED) 
    UUID user,
    @Column("data") 
    String data, 
    @Column("last_edit") 
    Instant lastEdit
    ) implements MapIdentifiable {

    public static CachedProject fromProject(Project backingProject) {
        return new CachedProject(backingProject.user(), backingProject.data(), backingProject.lastEdit());
    }

    public CachedProject update(String data) {
        return new CachedProject(this.user(), data, Instant.now());
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("user",this.user());
    }

    public static MapId id(UUID user) {
        return BasicMapId.id().with("user",user);
    }

    
}
