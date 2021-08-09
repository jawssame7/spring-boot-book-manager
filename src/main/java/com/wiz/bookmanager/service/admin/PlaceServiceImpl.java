package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.model.Place;
import com.wiz.bookmanager.repository.admin.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 保管場所のオプションを取得
     *
     * @return
     */
    @Override
    public List<Place> getOptionList() {

        return placeRepository.findAll(Specification.where(deletedAtContains(false)));
    }
}
