package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.form.PlaceForm;
import com.wiz.bookmanager.form.PlaceSearchForm;
import com.wiz.bookmanager.model.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 保管場所 サービス
 */
public interface PlaceService {

    /**
     * 保管場所の一覧
     * @param placeSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @return
     */
    public Page<Place> getPlaces(PlaceSearchForm placeSearchForm, Pageable pageable);

    /**
     * 保管場所の作成
     * @param placeForm フォーム
     * @return
     */
    public Place doInsert(PlaceForm placeForm);

    /**
     * idをキーに保管場所情報を取得する
     * @param id id
     * @return
     */
    public PlaceForm getPlace(Long id);

    /**
     * 保管場所を更新する
     * @param placeForm フォーム
     * @return
     */
    public Place editPlace(PlaceForm placeForm);

    /**
     * idをキーに保管場所情報を削除する 論理削除
     * @param id id
     * @return
     */
    public Place deletePlace(Long id);

    /**
     * 保管場所のオプションを取得
     * @return
     */
    public List<Place> getOptionList();
}
