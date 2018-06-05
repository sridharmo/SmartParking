package com.techm.smart.parking.solution.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.techm.smart.parking.solution.validator.ControllerValidator;
import com.techm.smart.parking.solution.validator.PSHistoricalOccupancyValidator;
import com.techm.smart.parking.solution.validator.PSLiveOccupancyValidator;
import com.techm.smart.parking.solution.validator.ParkingSlotValidator;
import com.techm.smart.parking.solution.validator.ParkingZoneValidator;
import com.techm.smart.parking.solution.validator.PersonValidator;
import com.techm.smart.parking.solution.validator.TagValidator;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource(value = { "classpath:cassandra.properties" })
@ComponentScan(basePackages = { "com.techm.smart.parking.solution" }, excludeFilters = { 
	    @Filter(type = FilterType.ANNOTATION, value = Configuration.class)
	  })
@EnableCassandraRepositories(basePackages = { "com.techm.smart.parking.solution.database.repo" })
public class AppConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private Environment environment;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new PersonValidator()).addPathPatterns("/person");
		registry.addInterceptor(new ParkingSlotValidator()).addPathPatterns("/pkslot");
		registry.addInterceptor(new ParkingZoneValidator()).addPathPatterns("/pkzone");
		registry.addInterceptor(new PSHistoricalOccupancyValidator()).addPathPatterns("/occupy/historical");
		registry.addInterceptor(new PSLiveOccupancyValidator()).addPathPatterns("/occupy");
		registry.addInterceptor(new TagValidator()).addPathPatterns("/tag");
		registry.addInterceptor(new ControllerValidator()).addPathPatterns("/task");
	}

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(environment.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		return new BasicCassandraMappingContext();
	}

	@Bean
	public CassandraConverter converter() {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(environment.getProperty("cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.NONE);
		return session;
	}

	@Bean
	public CassandraOperations cassandraTemplate() throws Exception {
		return new CassandraTemplate(session().getObject());
	}
	
	@Bean
	public Docket exposedAPIs() {
		return new Docket(DocumentationType.SPRING_WEB).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	 
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
