package com.wiz.bookmanager.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * ストレージのベースディレクトリパス
     * application.properties からロード
     */
    //private String location = "src/main/resources/static/thumbnails";
    @Value("${com.wiz.bookmanager.upload.dir.location}")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
