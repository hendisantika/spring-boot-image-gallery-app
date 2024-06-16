package id.my.hendisantika.imagegalleryapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-image-gallery-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/06/24
 * Time: 15.41
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
public class LogController {
    @RequestMapping("/logs")
    public String logMethod() {
        log.info("Hello from Spring Boot Logging Application.");
        log.info("This is sample info message");
        log.warn("This is sample warn message");
        log.error("This is sample error message");

        return "Hello Simplifying Tech";
    }
}
