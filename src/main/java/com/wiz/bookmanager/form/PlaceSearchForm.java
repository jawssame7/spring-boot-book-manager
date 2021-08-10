package com.wiz.bookmanager.form;

import lombok.Data;

/**
 * 保管場所 検索 フォーム
 */
@Data
public class PlaceSearchForm {

    /**
     * 名前
     */
    private String name;

    /**
     * 削除済みを含めるかどうか
     */
    private Boolean includeDeleted;
}
