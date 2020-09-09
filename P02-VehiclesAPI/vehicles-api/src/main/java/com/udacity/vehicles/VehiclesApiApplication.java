package com.udacity.vehicles;

import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Launches a Spring Boot application for the Vehicles API, initializes the car manufacturers in the
 * database, and launches web clients to communicate with maps and pricing.
 */
@SpringBootApplication
@EnableJpaAuditing  // todo: what is this?
@EnableEurekaServer
@EnableSwagger2
public class VehiclesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    /**
     * Initializes the car manufacturers available to the Vehicle API.
     *
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
    @Bean
    CommandLineRunner initDatabase(ManufacturerRepository repository) {
        return args -> {
            repository.save(new Manufacturer(100, "Audi"));
            repository.save(new Manufacturer(101, "Chevrolet"));
            repository.save(new Manufacturer(102, "Ford"));
            repository.save(new Manufacturer(103, "BMW"));
            repository.save(new Manufacturer(104, "Dodge"));
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     *
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name = "maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        String pricingService = "map-service";
//        return WebClient.create(endpoint);
        return WebClient.create(pricingService);
    }

    /**
     * Web Client for the pricing API
     *
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
    @Bean(name = "pricing")
    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint) {
        return WebClient.create("pricing-service");
    }

    @Bean
    public Docket vehicleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.udacity"))
                .paths(PathSelectors.ant("/cars/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Vehicle Api Doc", "", "1v", ApiInfo.DEFAULT
                .getTermsOfServiceUrl(),
                new Contact("Vimal Singh", "https://github.com/vimal-s", ""), ApiInfo.DEFAULT
                .getLicense(), ApiInfo.DEFAULT.getLicenseUrl(), Collections.emptyList());
    }
}
