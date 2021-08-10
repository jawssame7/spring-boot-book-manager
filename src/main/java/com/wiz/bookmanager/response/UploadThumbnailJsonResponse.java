package com.wiz.bookmanager.response;

import lombok.Data;

@Data
public class UploadThumbnailJsonResponse {

    private Boolean success;

    private String message;

    private String filePath;

    private String fileName;
}
