package id.my.hendisantika.imagegalleryapp.controller;

import id.my.hendisantika.imagegalleryapp.entity.ImageGallery;
import id.my.hendisantika.imagegalleryapp.service.ImageGalleryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/image/display/{id}")
    @ResponseBody
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<ImageGallery> imageGallery)
            throws ServletException, IOException {
        log.info("Id :: {}", id);
        imageGallery = imageGalleryService.getImageById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(imageGallery.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/image/imageDetails")
    public String showProductDetails(@RequestParam("id") Long id, Optional<ImageGallery> imageGallery, Model model) {
        try {
            log.info("Id :: {}", id);
            if (id != 0) {
                imageGallery = imageGalleryService.getImageById(id);

                log.info("products :: {}", imageGallery);
                if (imageGallery.isPresent()) {
                    model.addAttribute("id", imageGallery.get().getId());
                    model.addAttribute("description", imageGallery.get().getDescription());
                    model.addAttribute("name", imageGallery.get().getName());
                    model.addAttribute("price", imageGallery.get().getPrice());
                    return "imagedetails";
                }
                return "redirect:/home";
            }
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home";
        }
    }

    @GetMapping("/image/show")
    public String show(Model map) {
        List<ImageGallery> images = imageGalleryService.getAllActiveImages();
        map.addAttribute("images", images);
        return "images";
    }
}
