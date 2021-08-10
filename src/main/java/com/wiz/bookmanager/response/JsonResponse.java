package com.wiz.bookmanager.response;

import lombok.Data;

import java.util.Map;

@Data
public class JsonResponse {


    /**
     * 成功かどうか
     */
    private Boolean success;

    /**
     * メッセージ
     */
    private String message;

    /**
     * エラーメッセージ
     */
    private Map<String, String> errors;
}
