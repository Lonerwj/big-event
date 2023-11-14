package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.utils.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article getById(Integer id);

    void update(Article article);
}
