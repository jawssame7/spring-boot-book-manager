package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.model.Place;

import java.util.List;

public interface PlaceService {

    /**
     * 保管場所のオプションを取得
     * @return
     */
    public List<Place> getOptionList();
}
