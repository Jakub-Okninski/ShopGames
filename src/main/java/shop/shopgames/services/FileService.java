package shop.shopgames.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

@Service
public class FileService {
    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${photo.dir}")
    private String photoDir;

    public String saveFile(MultipartFile multipartFile) {
        init();
        String fileName = "";
        String fileNameNew= "";
        try {
            new File(photoDir).mkdirs();
            fileName = multipartFile.getOriginalFilename();

            int lastDotIndex = fileName.lastIndexOf('.');
            String type = fileName.substring(lastDotIndex + 1);

            Random random = new Random();
            fileNameNew ="File"+random.nextInt(1000000)+"."+type;
            var path = Path.of(photoDir, fileNameNew);
            while (Files.exists(path)) {
                fileNameNew ="File"+random.nextInt(1000000)+"."+type;
                path = Path.of(photoDir, fileNameNew);
            }

            System.out.println(path);
            System.out.println("laaaaaaaaaaaaaaaaaaaaaaaaaaa");

            var fos = new FileOutputStream(path.toFile());
            fos.write(multipartFile.getBytes());
            fos.close();

            System.out.println(fileName);
        } catch (Exception e) {
            // Obsłuż błędy
            e.printStackTrace();
        }

        return fileNameNew;
    }

    public void deleteFile(String fileName) {
        init();
        Path filePath = Paths.get(photoDir, fileName);
        System.out.println("Usuwanei");

        try {
            // Sprawdź, czy plik istnieje, zanim go usuniesz
            if (Files.exists(filePath)) {
                // Usuń plik
                Files.delete(filePath);
                System.out.println("Plik został pomyślnie usunięty.");
            } else {
                System.out.println("Plik nie istnieje.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Nie udało się usunąć pliku.");
        }
    }


    public String getFile(String fileName) {
        init();
        String file ="";
        try {
            var path = Path.of(photoDir, fileName);
            byte[] imageData = Files.readAllBytes(path);
            file = Base64.getEncoder().encodeToString(imageData);
        }catch (Exception e){

        }

        return file;
    }

    public void init() {
        Resource resource = resourceLoader.getResource("classpath:/");
        try {
            String projectPath = resource.getFile().getAbsolutePath();
            photoDir = Paths.get(projectPath, "file").toString();
            String toRemove = "\\target\\classes";
            String newPath = photoDir.replace(toRemove, "");
            System.out.println(newPath);
            photoDir=newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}