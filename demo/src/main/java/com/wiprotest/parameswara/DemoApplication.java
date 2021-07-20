package com.wiprotest.parameswara;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("demo")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/demo.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Wipro Scenario based test with Swagger")
				.description("Simple Demo Distributed Application using kafka")
				.license("APL V2")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("2.0")
				.build();
	}
	
	@Bean
    public NewTopic topic() {
        return TopicBuilder.name("demo")
                .partitions(10)
                .replicas(1)
                .build();
    }

//    @KafkaListener(id = "myId", topics = "demo")
//    public void listen(String in) {
//        System.out.println(in);
//    }
//    
//    @Bean
//    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
//        return args -> {
//            template.send("demo", "test");
//        };
//    }
}
