package handlers;

import model.request.FileUploadRequest;
import model.response.FileUploadResponse;

public interface IFileUploadHandler {

    FileUploadResponse handle(FileUploadRequest request);

}