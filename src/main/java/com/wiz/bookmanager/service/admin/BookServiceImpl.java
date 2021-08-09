package com.wiz.bookmanager.service.admin;
import com.wiz.bookmanager.form.BookForm;
import com.wiz.bookmanager.form.BookSearchForm;
import com.wiz.bookmanager.model.Book;
import com.wiz.bookmanager.repository.admin.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.wiz.bookmanager.specification.admin.BookSpecifications.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    /**
     * リポジトリ
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * 書籍の一覧
     * @param bookSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @return
     */
    @Override
    public Page<Book> getBooks(BookSearchForm bookSearchForm, Pageable pageable) {
        String name = bookSearchForm.getName();
        Boolean includeDeleted = bookSearchForm.getIncludeDeleted();
        return bookRepository.findAll(
                                Specification
                                        .where(nameContains(name))
                                        .and(deletedAtContains(includeDeleted)),
                                pageable);
    }

    /**
     * 書籍の作成
     *
     * @param bookForm bookForm
     * @return
     */
    @Override
    public Book doInsert(BookForm bookForm) {
        Book book = new Book();
        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(bookForm, book);
        return bookRepository.save(book);
    }

    /**
     * idをキーに書籍の詳細を取得する
     *
     * @param id
     * @return
     */
    @Override
    public BookForm getBook(Long id) {
        Book book = bookRepository.getById(id);
        BookForm bookForm = new BookForm();

        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(book, bookForm);
        return bookForm;
    }

    /**
     * 書籍情報を更新する
     *
     * @param bookForm
     * @return
     */
    @Override
    public Book editBook(BookForm bookForm) {
        Book book = new Book();
        // 同じプロパティ同士で内容をコピー
        BeanUtils.copyProperties(bookForm, book);
        return bookRepository.save(book);
    }

    /**
     * idをキーに書籍を削除する 論理削除
     *
     * @param id
     * @return
     */
    @Override
    public Book deleteBook(Long id) {
        Book book = bookRepository.getById(id);
        book.addDeletedAt();
        return bookRepository.save(book);
    }


}
