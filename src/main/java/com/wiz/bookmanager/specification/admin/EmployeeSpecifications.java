package com.wiz.bookmanager.specification.admin;

import com.wiz.bookmanager.model.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * 使用者 検索条件
 */
public class EmployeeSpecifications {

    /**
     * 名前を含む検索する。
     */
    public static Specification<Employee> nameContains(String name) {
        return ObjectUtils.isEmpty(name) ? null : new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    /**
     * 削除済みを含む検索する。
     */
    public static Specification<Employee> deletedAtContains(Boolean includeDeleted) {
        includeDeleted = !ObjectUtils.isEmpty(includeDeleted) && includeDeleted;
        return includeDeleted ? null : new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNull(root.get("deletedAt"));
            }
        };
    }
}
