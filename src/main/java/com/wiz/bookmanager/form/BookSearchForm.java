package com.wiz.bookmanager.form;

import lombok.Data;

/**
 * 書籍 検索 フォーム
 */
@Data
public class BookSearchForm {

    /**
     * 名前
     */
    private String name;

    /**
     * 削除済みを含めるかどうか
     */
    private Boolean includeDeleted;
}
