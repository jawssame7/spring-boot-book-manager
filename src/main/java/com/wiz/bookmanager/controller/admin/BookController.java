package com.wiz.bookmanager.controller.admin;

import com.wiz.bookmanager.form.BookForm;
import com.wiz.bookmanager.form.BookSearchForm;
import com.wiz.bookmanager.form.EmployeeForm;
import com.wiz.bookmanager.model.Book;
import com.wiz.bookmanager.model.Employee;
import com.wiz.bookmanager.model.Place;
import com.wiz.bookmanager.response.UploadThumbnail;
import com.wiz.bookmanager.service.admin.BookService;
import com.wiz.bookmanager.service.admin.EmployeeService;
import com.wiz.bookmanager.service.admin.PlaceService;
import com.wiz.bookmanager.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookController {

    /**
     * 書籍 サービスクラス
     */
    @Autowired
    private BookService bookService;

    /**
     * 保管者 サービスクラス
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 保管場所 サービスクラス
     */
    @Autowired
    private PlaceService placeService;

    /**
     * ストレージ サービスクラス
     */
    @Autowired
    private StorageService storageService;

    /**
     * 書籍一覧(ページング) アクション
     * 戻り値をログで出したいため必ずModelAndViewを使用
     * @param bookSearchForm 検索フォーム
     * @param pageable ページングオブジェクト
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/book")
    public ModelAndView index (@ModelAttribute BookSearchForm bookSearchForm,
                               @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                               ModelAndView mav) {

        // test start
//        mav.addObject("hoge", "test1");
//        mav.addObject("huga", "test2");

//        List<Place> placeList = new ArrayList<Place>();
//        Place place = new Place();
//        place.setId(1L);
//        place.setName("test");
//        placeList.add(place);
//        mav.addObject("placeList", placeList);
//        mav.addObject("placeObj", place);

        // test end

        Page<Book> bookPage = bookService.getBooks(bookSearchForm, pageable);

        // pageという名前で、変数:bookPageを設定
        // 画面側では、${page} 等で中身を取り出せる
        mav.addObject("page", bookPage);
        mav.addObject("books", bookPage.getContent());

        // 表示するview
        mav.setViewName("admin/book/index");

        return mav;
    }

    /**
     * 新規作成 アクション
     * @param mav
     * @return
     */
    @GetMapping("/book/create")
    public ModelAndView create (@ModelAttribute BookForm bookForm, ModelAndView mav) {

        mav.setViewName("admin/book/create");

        return mav;
    }

    /**
     * サムネイルをファイルアップロードする 非同期
     * @param uploadFile ファイル
     * @return json
     */
    @PostMapping("/book/uploadThumbnail")
    @ResponseBody
    public UploadThumbnail uploadThumbnail(@RequestParam("file") final MultipartFile uploadFile) {
        UploadThumbnail thumbnail = new UploadThumbnail();
        if (uploadFile.isEmpty()) {
            thumbnail.setSuccess(false);
            thumbnail.setMessage("ファイルがありません");
            return thumbnail;
        }

        Path newFile = storageService.store(uploadFile);
        thumbnail.setSuccess(true);
        thumbnail.setMessage("ファイルアップロード成功");
        thumbnail.setFileName(newFile.getFileName().toString());
        thumbnail.setFilePath("/public/thumbnails");

        return thumbnail;
    }

    /**
     * 作成 アクション
     * @param bookForm 書籍フォーム
     * @param errorResult エラーオブジェクト
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/book/create")
    public ModelAndView store(@Validated @ModelAttribute BookForm bookForm,
                              BindingResult errorResult,
                              ModelAndView mav,
                              RedirectAttributes attributes) {
        // 入力チェック
        if (errorResult.hasErrors()) {
            mav.setViewName("admin/book/create");
            return mav;
        }

        // インサート
        Book book = bookService.doInsert(bookForm);

        if (book == null) {
            attributes.addFlashAttribute("message", "作成に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "作成しました。");
        }

        mav.setViewName("redirect:/admin/book");
        return mav;
    }

    /**
     * 書籍　編集 アクション
     * @param id id
     * @param mav モデルアンドビュー
     * @return モデルアンドビュー
     */
    @GetMapping("/book/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView mav) {

        BookForm bookForm = bookService.getBook(id);

        // オプションの一覧取得
        List<Place> placeList = placeService.getOptionList();
        List<Employee> employeeList = employeeService.getOptionList();

        mav.addObject("bookForm", bookForm);
        mav.addObject("placeList", placeList);
        mav.addObject("employeeList", employeeList);
        mav.setViewName("admin/book/edit");
        return mav;
    }

    /**
     * 書籍 編集 アクション
     * @param bookForm 書籍フォーム
     * @param errorResult エラーオブジェクト
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/book/edit")
    public ModelAndView editEmployee(@Validated @ModelAttribute BookForm bookForm,
                                     BindingResult errorResult,
                                     ModelAndView mav,
                                     RedirectAttributes attributes) {
        // 入力チェック
        if (errorResult.hasErrors()) {
            mav.setViewName("admin/book/edit");
            return mav;
        }

        // アップデート
        Book book = bookService.editBook(bookForm);

        if (book == null) {
            attributes.addFlashAttribute("message", "更新に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "更新しました。");
        }

        mav.setViewName("redirect:/admin/book");
        return mav;
    }

    /**
     * 保管者 削除 アクション
     * @param id id
     * @param mav モデルアンドビュー
     * @param attributes リダイレクトアトリビュート
     * @return モデルアンドビュー
     */
    @PostMapping("/book/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, ModelAndView mav, RedirectAttributes attributes) {

        Book book = bookService.deleteBook(id);

        if (book == null) {
            attributes.addFlashAttribute("message", "削除に失敗しました。");
        } else {
            attributes.addFlashAttribute("message", "削除しました。");
        }

        mav.setViewName("redirect:/admin/book");
        return mav;
    }
}
