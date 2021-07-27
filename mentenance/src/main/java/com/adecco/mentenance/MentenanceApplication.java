package com.adecco.mentenance;

import com.adecco.mentenance.storage.FileSystemStorageService;
import com.adecco.mentenance.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class MentenanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentenanceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileSystemStorageService storageService){
        return (args) -> {
            //storageService.deleteAll();
            storageService.init();
        };
    }
}
