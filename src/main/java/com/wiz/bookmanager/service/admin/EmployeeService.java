package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.form.EmployeeForm;
import com.wiz.bookmanager.form.EmployeeSearchForm;
import com.wiz.bookmanager.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    /**
     * 使用者の一覧
     * @param employeeSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @return
     */
    public Page<Employee> getEmployees(EmployeeSearchForm employeeSearchForm, Pageable pageable);

    /**
     * 使用者の作成
     * @param employeeForm employeeForm
     * @return
     */
    public Employee doInsert(EmployeeForm employeeForm);

    /**
     * idをキーに使用者情報を取得する
     * @param id
     * @return
     */
    public EmployeeForm getEmployee(Long id);

    /**
     * 使用者を更新する
     * @param employeeForm
     * @return
     */
    public Employee editEmployee(EmployeeForm employeeForm);

    /**
     * idをキーに使用者情報を削除する 論理削除
     * @param id
     * @return
     */
    public Employee deleteEmployee(Long id);

    /**
     * 保管場所のオプションを取得
     * @return
     */
    public List<Employee> getOptionList();

}
