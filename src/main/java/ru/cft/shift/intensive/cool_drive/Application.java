package ru.cft.shift.intensive.cool_drive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.File;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        File root = new File("/storage");
        if (root.mkdir()) {
            System.out.println("Создаю корневую директорию");
        }
        SpringApplication.run(Application.class, args);
    }
}
