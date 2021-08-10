package com.wiz.bookmanager.controller.admin;

import com.wiz.bookmanager.form.PlaceForm;
import com.wiz.bookmanager.form.PlaceSearchForm;
import com.wiz.bookmanager.model.Place;
import com.wiz.bookmanager.response.PlaceJsonResponse;
import com.wiz.bookmanager.service.admin.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Controller
@RequestMapping("/admin")
public class PlaceController {

    /**
     * 保管場所サービスクラス
     */
    @Autowired
    private PlaceService placeService;

    /**
     * 一覧 アクション
     * @param placeSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/place")
    public ModelAndView index(@ModelAttribute PlaceSearchForm placeSearchForm,
                              @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                              ModelAndView mav) {

        Page<Place> placePage = placeService.getPlaces(placeSearchForm, pageable);

        // pageという名前で、変数:empPageを設定
        // 画面側では、${page} 等で中身を取り出せる
        mav.addObject("page", placePage);
        mav.addObject("places", placePage.getContent());

        // 検索時のパラメータを保持
        mav.addObject("placeSearchForm", placeSearchForm);

        //
        mav.addObject("placeForm", new PlaceForm());

        mav.setViewName("admin/place/index");
        return mav;
    }

    /**
     * 作成 アクション 非同期
     * @param placeForm フォーム
     * @param errorResult エラーオブジェクト
     * @return Jsonレスポンスデータ
     */
    @PostMapping("/place/create")
    @ResponseBody
    public PlaceJsonResponse store(@Validated @RequestBody PlaceForm placeForm,
                                    BindingResult errorResult) {

        PlaceJsonResponse placeJsonResponse = new PlaceJsonResponse();
        placeJsonResponse.setName(placeForm.getName());

        // 入力チェック
        if (errorResult.hasErrors()) {
            // エラーメッセージをマップに詰め替え
            Map<String, String> errors = errorResult.getFieldErrors().stream()
                                            .collect(
                                                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                                            );

            placeJsonResponse.setSuccess(false);
            placeJsonResponse.setMessage("入力エラーがあります。");
            placeJsonResponse.setErrors(errors);

            return placeJsonResponse;
        }

        // インサート
        Place place = placeService.doInsert(placeForm);

        if (place == null) {
            throw new RuntimeException("作成に失敗しました。");
        } else {
            placeJsonResponse.setSuccess(true);
            placeJsonResponse.setMessage("作成しました。");
        }


        return placeJsonResponse;
    }

    /**
     * 編集 アクション 非同期
     * @param placeForm フォーム
     * @param errorResult エラーオブジェクト
     * @return Jsonレスポンスデータ
     */
    @PostMapping("/place/{id}/edit")
    @ResponseBody
    public PlaceJsonResponse edit(@PathVariable Long id,
                                    @Validated @RequestBody PlaceForm placeForm,
                                    BindingResult errorResult) {

        PlaceJsonResponse placeJsonResponse = new PlaceJsonResponse();
        placeJsonResponse.setId(placeForm.getId());
        placeJsonResponse.setName(placeForm.getName());

        // 入力チェック
        if (errorResult.hasErrors()) {
            // エラーメッセージをマップに詰め替え
            Map<String, String> errors = errorResult.getFieldErrors().stream()
                    .collect(
                            Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
                    );

            placeJsonResponse.setSuccess(false);
            placeJsonResponse.setMessage("入力エラーがあります。");
            placeJsonResponse.setErrors(errors);

            return placeJsonResponse;
        }

        // 更新
        Place place = placeService.editPlace(placeForm);

        if (place == null) {
            throw new RuntimeException("更新に失敗しました。");
        } else {
            placeJsonResponse.setSuccess(true);
            placeJsonResponse.setMessage("更新しました。");
        }



        return placeJsonResponse;
    }

    /**
     * 削除 アクション 同期
     * @param id id
     * @return Jsonレスポンスデータ
     */
    @PostMapping("/place/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, ModelAndView mav, RedirectAttributes attributes) {

        Place place = placeService.deletePlace(id);

        if (place == null) {
            attributes.addFlashAttribute("message", "削除に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "削除しました。");
        }

        mav.setViewName("redirect:/admin/place");
        return mav;
    }
}
