package jbazann.catchyname.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories("jbazann.catchyname.server.persistence.cassandra")
@ComponentScan("jbazann.catchyname.server")
public class CatchyNameServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatchyNameServerApplication.class, args);
	}

}
