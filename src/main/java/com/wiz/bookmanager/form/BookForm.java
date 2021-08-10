package com.wiz.bookmanager.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 書籍 フォーム
 */
@Data
public class BookForm {

    /**
     * id
     */
    private Long id;

    /**
     * 名前
     */
    @NotBlank(message = "名前の入力は必須です。")
    @Size(min=1, max=255)
    private String name;

    /**
     * 説明
     */
    @Size(max=512)
    private String description;

    /**
     * サムネイル
     */
    @Size(max=255)
    private String thumbnail;

    /**
     * 保管場所
     */
    private Long placeId;

    /**
     * 使用者
     */
    private Long employeeId;
}
