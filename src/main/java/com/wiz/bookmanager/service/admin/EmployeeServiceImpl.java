package com.wiz.bookmanager.service.admin;

import com.wiz.bookmanager.form.EmployeeForm;
import com.wiz.bookmanager.form.EmployeeSearchForm;
import com.wiz.bookmanager.model.Employee;
import com.wiz.bookmanager.repository.admin.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import static com.wiz.bookmanager.specification.admin.EmployeeSpecifications.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 使用者 サービスクラス
 *
 */
@Service
@Transactional // メソッド開始時にトランザクションを開始、終了時にコミットする
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * リポジトリ
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * 使用者の一覧
     * @param employeeSearchForm 検索フォーム
     * @param pageable pageable
     * @return
     */
    @Override
    public Page<Employee> getEmployees(EmployeeSearchForm employeeSearchForm, Pageable pageable) {
        String name = employeeSearchForm.getName();
        Boolean includeDeleted = employeeSearchForm.getIncludeDeleted();
        return employeeRepository.findAll(Specification.where(nameContains(name)).and(deletedAtContains(includeDeleted)), pageable);
    }

    /**
     * 使用者の作成
     * @param employeeForm employeeForm
     * @return
     */
    @Override
    public Employee doInsert(EmployeeForm employeeForm) {
        Employee employee = new Employee();
        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(employeeForm, employee);
        // インサート
        return employeeRepository.save(employee);
    }

    /**
     * idをキーに使用者情報を取得する
     * @param id
     * @return
     */
    @Override
    public EmployeeForm getEmployee(Long id) {
        Employee employee = employeeRepository.getById(id);
        EmployeeForm employeeForm = new EmployeeForm();

        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(employee, employeeForm);

        return employeeForm;
    }

    /**
     * 使用者を更新する
     * @param employeeForm
     * @return
     */
    @Override
    public Employee editEmployee(EmployeeForm employeeForm) {
        Employee employee = new Employee();
        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(employeeForm, employee);

        return employeeRepository.save(employee);
    }

    /**
     * idをキーに使用者情報を削除する 論理削除
     * @param id
     * @return
     */
    @Override
    public Employee deleteEmployee(Long id) {
        Employee employee = employeeRepository.getById(id);
        employee.addDeletedAt();
        return employeeRepository.save(employee);
    }

    /**
     * 保管場所のオプションを取得
     *
     * @return
     */
    @Override
    public List<Employee> getOptionList() {
        return employeeRepository.findAll(Specification.where(deletedAtContains(false)));
    }
}
