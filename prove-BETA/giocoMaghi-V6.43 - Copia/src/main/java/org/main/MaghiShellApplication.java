package org.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.classi", "org.shell", "org.main"})
public class MaghiShellApplication {

    public static void main(String[] args) {
        System.out.println("🧙‍♂️ Avvio Gioco dei Maghi...");
        SpringApplication.run(MaghiShellApplication.class, args);
    }
}