package id.my.hendisantika.imagegalleryapp.controller;

import id.my.hendisantika.imagegalleryapp.entity.ImageGallery;
import id.my.hendisantika.imagegalleryapp.service.ImageGalleryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Date;

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

    @GetMapping(value = {"/", "/home"})
    public String addProductPage() {
        return "index";
    }

    @PostMapping("/image/saveImageDetails")
    public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name,
                                                         @RequestParam("price") BigDecimal price,
                                                         @RequestParam("description") String description,
                                                         Model model, HttpServletRequest request,
                                                         final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: {}", uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: {}", file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("Name: {} {}", names[0], filePath);
            log.info("description: {}", descriptions[0]);
            log.info("price: {}", price);
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            ImageGallery imageGallery = new ImageGallery();
            imageGallery.setName(names[0]);
            imageGallery.setImage(imageData);
            imageGallery.setPrice(price);
            imageGallery.setDescription(descriptions[0]);
            imageGallery.setCreateDate(createDate);
            imageGalleryService.saveImage(imageGallery);
            log.info("HttpStatus==={}", new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: {}", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
