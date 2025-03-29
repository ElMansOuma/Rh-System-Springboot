package com.RH.gestion_collaborateurs.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le répertoire où les fichiers téléchargés seront stockés.", ex);
        }
    }

    // Méthode pour créer un répertoire s'il n'existe pas
    public void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path path = this.fileStorageLocation.resolve(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    // Méthode modifiée pour stocker le fichier dans un sous-dossier spécifique
    public String storeFile(MultipartFile file, String subDirectory, String prefix) {
        try {
            // Vérifier le contenu du fichier
            if (file.getOriginalFilename() == null) {
                throw new RuntimeException("Nom de fichier invalide.");
            }

            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);

            // Créer un nom de fichier unique
            String newFileName = prefix + "-" + UUID.randomUUID().toString() + "." + extension;

            // Résoudre le chemin complet
            Path subDirectoryPath = this.fileStorageLocation.resolve(subDirectory);

            // S'assurer que le sous-répertoire existe
            if (!Files.exists(subDirectoryPath)) {
                Files.createDirectories(subDirectoryPath);
            }

            // Copier le fichier vers le répertoire de destination
            Path targetLocation = subDirectoryPath.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Retourner le chemin relatif pour le stockage en base de données
            return subDirectory + "/" + newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Échec du stockage du fichier: " + ex.getMessage(), ex);
        }
    }

    public void deleteFile(String filePath) {
        try {
            Path fullPath = this.fileStorageLocation.resolve(filePath);
            Files.deleteIfExists(fullPath);
        } catch (IOException ex) {
            throw new RuntimeException("Échec de la suppression du fichier: " + ex.getMessage(), ex);
        }
    }

    public Path getFilePath(String filePath) {
        return this.fileStorageLocation.resolve(filePath);
    }
}