package com.wiz.bookmanager.specification.admin;

import com.wiz.bookmanager.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 書籍 検索条件
 */
public class BookSpecifications {

    /**
     * 名前を含む検索する。
     */
    public static Specification<Book> nameContains(String name) {
        return ObjectUtils.isEmpty(name) ? null : new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    /**
     * 削除済みを含む検索する。
     */
    public static Specification<Book> deletedAtContains(Boolean includeDeleted) {
        includeDeleted = !ObjectUtils.isEmpty(includeDeleted) && includeDeleted;
        return includeDeleted ? null : new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNull(root.get("deletedAt"));
            }
        };
    }
}
