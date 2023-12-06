package jbazann.catchyname.server.persistence.cassandra;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import jbazann.catchyname.server.model.Project;
import jbazann.catchyname.server.persistence.cassandra.custom.CustomProjectRepository;

@Repository
public interface ProjectRepository extends MapIdCassandraRepository<Project>, 
	CustomProjectRepository {}