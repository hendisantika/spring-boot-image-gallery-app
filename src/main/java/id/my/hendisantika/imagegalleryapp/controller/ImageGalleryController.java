package id.my.hendisantika.imagegalleryapp.controller;

import id.my.hendisantika.imagegalleryapp.service.ImageGalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-image-gallery-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/06/24
 * Time: 15.10
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageGalleryController {

    @Value("${uploadDir}")
    private String uploadFolder;

    private ImageGalleryService imageGalleryService;

}
