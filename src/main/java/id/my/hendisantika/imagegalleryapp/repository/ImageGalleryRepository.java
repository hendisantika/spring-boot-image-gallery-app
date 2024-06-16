package id.my.hendisantika.imagegalleryapp.repository;

import id.my.hendisantika.imagegalleryapp.entity.ImageGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-image-gallery-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 16/06/24
 * Time: 14.52
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface ImageGalleryRepository extends JpaRepository<ImageGallery, Long> {
}
