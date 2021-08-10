package com.wiz.bookmanager.service.admin;


import com.wiz.bookmanager.form.BookForm;
import com.wiz.bookmanager.form.BookSearchForm;
import com.wiz.bookmanager.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 書籍 サービス
 */
public interface BookService {

    /**
     * 書籍の一覧
     * @param bookSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @return
     */
    public Page<Book> getBooks(BookSearchForm bookSearchForm, Pageable pageable);

    /**
     * 書籍の作成
     * @param bookForm bookForm
     * @return
     */
    public Book doInsert(BookForm bookForm);

    /**
     * idをキーに書籍の詳細を取得する
     * @param id
     * @return
     */
    public BookForm getBook(Long id);

    /**
     * 書籍情報を更新する
     * @param bookForm
     * @return
     */
    public Book editBook(BookForm bookForm);

    /**
     * idをキーに書籍を削除する 論理削除
     * @param id
     * @return
     */
    public Book deleteBook(Long id);
}
