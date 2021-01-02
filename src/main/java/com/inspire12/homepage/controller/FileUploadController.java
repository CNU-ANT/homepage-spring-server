package com.inspire12.homepage.controller;

import com.inspire12.homepage.interceptor.MethodAllowLevel;
import com.inspire12.homepage.domain.model.FileMeta;
import com.inspire12.homepage.message.UploadFileResponse;
import com.inspire12.homepage.service.board.FileMetaService;
import com.inspire12.homepage.exception.StorageFileNotFoundException;
import com.inspire12.homepage.service.storage.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final FileSystemStorageService storageService;
    private final FileMetaService fileMetaService;

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @PostMapping("/files")
    public ResponseEntity handleFileUpload( @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        // file type 확인
        String uploadUrl = storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return ResponseEntity.ok().body(new UploadFileResponse(uploadUrl));
    }

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @DeleteMapping("/files")
    public ResponseEntity deleteFileUpload( @RequestParam("id") Integer id,
                                            RedirectAttributes redirectAttributes) {
        // file type 확인
        FileMeta fileMeta = fileMetaService.getFileMeta(id);
        fileMetaService.deleteFileMeta(id);

        return ResponseEntity.ok().body(id);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
