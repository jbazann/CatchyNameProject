package jbazann.catchyname.server.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.MapIdentifiable;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_projects")
public final record Project(
    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED) 
    UUID user, 
    @PrimaryKeyColumn(name = "last_edit", ordinal = 1, type = PrimaryKeyType.CLUSTERED) 
    Instant lastEdit,
    @Column("id")
    @Indexed
    UUID projectId,
    @Column("created")
    Instant created, 
    @Column("data")
    String data
    ) implements MapIdentifiable {

    public static Project newProject(UUID user, String data) {
        return new Project(user, Instant.now(),UUID.randomUUID(), Instant.now(), data);
    }

    public static Project updateFromCachedProject(Project outdated, CachedProject cached) {
        if(outdated.user() != cached.user());// TODO Exceptions
        return new Project(
            cached.user(),
            cached.lastEdit(), 
            outdated.projectId(),  
            outdated.created(), 
            cached.data());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Project p)
            return this.user().equals(p.user());
        return false;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("user",this.user()).with("lastEdit",this.lastEdit());
    }

    public static MapId id(UUID user, Instant lastEdit) {
        return BasicMapId.id().with("user",user).with("lastEdit",lastEdit);
    }

}
