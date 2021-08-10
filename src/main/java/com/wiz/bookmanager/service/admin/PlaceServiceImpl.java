package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.form.PlaceForm;
import com.wiz.bookmanager.form.PlaceSearchForm;
import com.wiz.bookmanager.model.Place;
import com.wiz.bookmanager.repository.admin.PlaceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.wiz.bookmanager.specification.admin.PlaceSpecifications.*;

import java.util.List;

@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * 保管場所の一覧
     *
     * @param placeSearchForm 検索フォーム
     * @param pageable        ページングオブジェクト
     * @return
     */
    @Override
    public Page<Place> getPlaces(PlaceSearchForm placeSearchForm, Pageable pageable) {
        String name = placeSearchForm.getName();
        Boolean includeDeleted = placeSearchForm.getIncludeDeleted();
        return placeRepository.findAll(Specification.where(nameContains(name))
                                .and(deletedAtContains(includeDeleted)), pageable);
    }

    /**
     * 保管場所の作成
     *
     * @param placeForm フォーム
     * @return
     */
    @Override
    public Place doInsert(PlaceForm placeForm) {
        Place place = new Place();

        BeanUtils.copyProperties(placeForm, place);

        return placeRepository.save(place);
    }

    /**
     * idをキーに保管場所情報を取得する
     *
     * @param id id
     * @return
     */
    @Override
    public PlaceForm getPlace(Long id) {
        Place place = placeRepository.getById(id);
        PlaceForm placeForm = new PlaceForm();

        BeanUtils.copyProperties(place, placeForm);
        return placeForm;
    }

    /**
     * 保管場所を更新する
     *
     * @param placeForm フォーム
     * @return
     */
    @Override
    public Place editPlace(PlaceForm placeForm) {
        Place place = new Place();

        BeanUtils.copyProperties(placeForm, place);

        return placeRepository.save(place);
    }

    /**
     * idをキーに保管場所情報を削除する 論理削除
     *
     * @param id id
     * @return
     */
    @Override
    public Place deletePlace(Long id) {
        Place place = placeRepository.getById(id);
        place.addDeletedAt();
        return placeRepository.save(place);
    }

    /**
     * 保管場所のオプションを取得
     *
     * @return
     */
    @Override
    public List<Place> getOptionList() {

        return placeRepository.findAll(Specification.where(deletedAtContains(false)));
    }
}
