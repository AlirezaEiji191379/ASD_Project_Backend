package ir.rama.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
@RequestMapping("/health")
public class TaskManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}
