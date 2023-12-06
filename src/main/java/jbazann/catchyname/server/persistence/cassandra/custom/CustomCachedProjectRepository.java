package jbazann.catchyname.server.persistence.cassandra.custom;

import java.util.UUID;

public interface CustomCachedProjectRepository {
    public boolean flush(UUID user);
}
