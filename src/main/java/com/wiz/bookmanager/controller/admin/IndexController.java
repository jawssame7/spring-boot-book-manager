package com.wiz.bookmanager.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * /admin がコールされた場合のコントローラー
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    /**
     * インデックス アクション
     * @return
     */
    @GetMapping("")
    public String index() {
        return "redirect:/admin/book";
    }

}
