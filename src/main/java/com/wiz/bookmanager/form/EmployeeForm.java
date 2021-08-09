package com.wiz.bookmanager.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


/**
 * 使用者 フォーム
 * lombokを使うことによりセッターゲッターを自動生成
 */
@Data
public class EmployeeForm {

    /**
     * id
     */
    private Long id;

    /**
     * 名前
     */
    @NotBlank(message = "名前の入力は必須です。")
    private String name;

    /**
     * メールアドレス
     */
    @NotBlank(message = "メールアドレス入力は必須です。")
    @Email(message = "メールアドレスの形式に入力してください。")
    private String email;

}
