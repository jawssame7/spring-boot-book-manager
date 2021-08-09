package com.wiz.bookmanager.response;

import lombok.Data;

@Data
public class UploadThumbnail {

    private Boolean success;

    private String message;

    private String filePath;

    private String fileName;
}
