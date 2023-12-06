package jbazann.catchyname.server.persistence.cassandra.custom;

import java.util.UUID;

import jbazann.catchyname.server.model.Project;

public interface CustomProjectRepository {
    public Project findFirstByUserOrderByLastEditDesc(UUID user);
}
