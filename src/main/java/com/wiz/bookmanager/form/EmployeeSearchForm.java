package com.wiz.bookmanager.form;

import lombok.Data;

/**
 * 使用者検索フォーム
 */
@Data
public class EmployeeSearchForm {

    /**
     * 名前
     */
    private String name;

    /**
     * 削除済みを含めるかどうか
     */
    private Boolean includeDeleted;
}
