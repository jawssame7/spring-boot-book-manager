package com.wiz.bookmanager.controller.admin;

import com.wiz.bookmanager.form.EmployeeForm;
import com.wiz.bookmanager.form.EmployeeSearchForm;
import com.wiz.bookmanager.model.Employee;
import com.wiz.bookmanager.service.admin.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 保管者 コントローラー
 */
@Controller
@RequestMapping("/admin")
public class EmployeeController {

    /**
     * 保管者サービスクラス
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 一覧 アクション
     * @param employeeSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/employee")
    public ModelAndView index(@ModelAttribute EmployeeSearchForm employeeSearchForm,
                              @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                      Pageable pageable, ModelAndView mav) {


        Page<Employee> empPage = employeeService.getEmployees(employeeSearchForm, pageable);

        // pageという名前で、変数:empPageを設定
        // 画面側では、${page} 等で中身を取り出せる
        mav.addObject("page", empPage);
        mav.addObject("employees", empPage.getContent());

        // 検索時のパラメータを保持
        mav.addObject("employeeSearchForm", employeeSearchForm);
//        mav.addObject("search_param_name", name);
//        mav.addObject("search_param_deleted_at", includeDeleted);

        mav.setViewName("admin/employee/index");
        return mav;
    }

    /**
     * 新規作成 アクション
     *
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/employee/create")
    public ModelAndView create(@ModelAttribute EmployeeForm employeeForm, ModelAndView mav) {


        // @ModelAttribute アノテーションを設定することで 下記と同じになる
        // mav.addObject("employeeForm", employeeForm);

        mav.setViewName("admin/employee/create");

        return mav;
    }

    /**
     * 作成 アクション
     * @param employeeForm 保管者フォーム
     * @param errorResult エラーオブジェクト
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/employee/create")
    public ModelAndView store(@Validated @ModelAttribute EmployeeForm employeeForm,
                              BindingResult errorResult,
                              ModelAndView mav,
                              RedirectAttributes attributes) {
        // 入力チェック
        if (errorResult.hasErrors()) {
            mav.setViewName("admin/employee/create");
            return mav;
        }

        // インサート
        Employee employee = employeeService.doInsert(employeeForm);

        if (employee == null) {
            attributes.addFlashAttribute("message", "作成に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "作成しました。");
        }

        mav.setViewName("redirect:/admin/employee");
        return mav;
    }

    /**
     * 保管者　編集 アクション
     * @param id id
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/employee/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView mav) {

        EmployeeForm employeeForm = employeeService.getEmployee(id);

        // employeeFormという名前で、変数:employeeFormを設定
        // 画面側では、${employeeForm} 等で中身を取り出せる
        mav.addObject("employeeForm", employeeForm);
        mav.setViewName("admin/employee/edit");
        return mav;
    }

    /**
     * 保管者 編集
     * @param employeeForm 保管者フォーム
     * @param errorResult エラーオブジェクト
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/employee/edit")
    public ModelAndView editEmployee(@Validated @ModelAttribute EmployeeForm employeeForm,
                                     BindingResult errorResult,
                                     ModelAndView mav,
                                     RedirectAttributes attributes) {
        // 入力チェック
        if (errorResult.hasErrors()) {
            mav.setViewName("admin/employee/edit");
            return mav;
        }

        // アップデート
        Employee employee = employeeService.editEmployee(employeeForm);

        if (employee == null) {
            attributes.addFlashAttribute("message", "更新に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "更新しました。");
        }

        mav.setViewName("redirect:/admin/employee");
        return mav;
    }

    /**
     * 保管者 削除 アクション
     * @param id id
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/employee/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, ModelAndView mav, RedirectAttributes attributes) {

        Employee employee = employeeService.deleteEmployee(id);

        if (employee == null) {
            attributes.addFlashAttribute("message", "削除に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "削除しました。");
        }

        mav.setViewName("redirect:/admin/employee");
        return mav;
    }
}
