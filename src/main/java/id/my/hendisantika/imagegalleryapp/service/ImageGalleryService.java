package id.my.hendisantika.imagegalleryapp.service;

import id.my.hendisantika.imagegalleryapp.repository.ImageGalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-image-gallery-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/06/24
 * Time: 14.53
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class ImageGalleryService {

    private final ImageGalleryRepository imageGalleryRepository;
}
