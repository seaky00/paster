package pw.seaky.paster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories

public class MongoConfiguration {


    public @Bean
    MongoClientFactoryBean mongo()  {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        return mongo;

    }
}




