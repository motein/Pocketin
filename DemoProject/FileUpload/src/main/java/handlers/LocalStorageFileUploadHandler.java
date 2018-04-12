package handlers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import exceptions.FileUploadException;
import model.errors.ServiceError;
import model.files.HttpFile;
import model.request.FileUploadRequest;
import model.response.FileUploadResponse;
import provider.IRootPathProvider;

@Component
public class LocalStorageFileUploadHandler implements IFileUploadHandler {

    private final IRootPathProvider rootPathProvider;

    @Autowired
    public LocalStorageFileUploadHandler(IRootPathProvider rootPathProvider) {
        this.rootPathProvider = rootPathProvider;
    }

    @Override
    public FileUploadResponse handle(FileUploadRequest request) {

        // Early exit, if there is no Request:
        if(request == null) {
            throw new FileUploadException(new ServiceError("missingFile", "Missing File data"), String.format("Missing Parameter: request"));
        }

        // Get the HttpFile:
        HttpFile httpFile = request.getHttpFile();

        // Early exit, if the Request has no data assigned:
        if(httpFile == null) {
            throw new FileUploadException(new ServiceError("missingFile", "Missing File data"), String.format("Missing Parameter: request.httpFile"));
        }

        // We don't override existing files, create a new UUID File name:
        String targetFileName = UUID.randomUUID().toString();

        // Write it to Disk:
        internalWriteFile(httpFile.getStream(), targetFileName);

        return new FileUploadResponse(targetFileName);
    }

    private void internalWriteFile(InputStream stream, String fileName) {
        try {
            Files.copy(stream, Paths.get(rootPathProvider.getRootPath(), fileName));
        } catch(Exception e) {
            throw new FileUploadException(new ServiceError("storingFileError", "Error writing file"), String.format("Writing File '%s' failed", fileName), e);
        }
    }
}
