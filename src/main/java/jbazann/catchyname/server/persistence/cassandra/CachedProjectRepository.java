package jbazann.catchyname.server.persistence.cassandra;

import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import jbazann.catchyname.server.model.CachedProject;
import jbazann.catchyname.server.persistence.cassandra.custom.CustomCachedProjectRepository;

@Repository
public interface CachedProjectRepository extends MapIdCassandraRepository<CachedProject>, 
	CustomCachedProjectRepository {}
