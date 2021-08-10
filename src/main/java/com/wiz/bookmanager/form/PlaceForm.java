package com.wiz.bookmanager.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 保管場所 フォーム
 */
@Data
public class PlaceForm {

    /**
     * id
     */
    private Long id;

    /**
     * 名前
     */
    @NotBlank(message = "名前の入力は必須です。")
    private String name;
}
