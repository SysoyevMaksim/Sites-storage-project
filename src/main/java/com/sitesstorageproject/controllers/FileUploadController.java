package com.sitesstorageproject.controllers;


import com.sitesstorageproject.entities.User;
import com.sitesstorageproject.repos.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class FileUploadController {

    private final UserRepository userRepo;

    public FileUploadController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping("/upload")
    public String uploadPage() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails details) throws IOException {
        User user = userRepo.findByEmail(details.getUsername());
        File destDir = new File("src/main/resources/" + user.getId().toString());

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(file.getInputStream(), Charset.forName("cp866"));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(destDir, zipEntry.getName());
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
        return "redirect:/";
    }
}
