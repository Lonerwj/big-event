package com.itheima.controller;

import com.itheima.pojo.Article;
import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.ArticleService;
import com.itheima.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,
                                         Integer pageSize,
                                         @RequestParam(required = false) Integer categoryId,
                                         @RequestParam(required = false) String state){
        PageBean<Article> pageBean = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pageBean);
    }

    @GetMapping("/detail")
    public Result detail(Integer id){
        Article article = articleService.getById(id);
        if (article==null){
            return Result.error("未找到该文章");
        }
        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }

}
