package jbazann.catchyname.server.persistence.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;

/**
 * CassandraConfiguration
 */
@Configuration
@PropertySource("/jbazann/catchyname/server/persistence/cassandra/cassandra.properties")
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getContactPoints() {
        return env.getProperty("cassandra.contactpoints");
    }

    @Override
    protected int getPort() {
        return Integer.parseInt(env.getProperty("cassandra.port"));    
    }

    @Override
    protected String getKeyspaceName() {
        return env.getProperty("cassandra.keyspace");
    }

    @Bean
	public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
		return new CassandraTemplate(sessionFactory, converter);
	}

}