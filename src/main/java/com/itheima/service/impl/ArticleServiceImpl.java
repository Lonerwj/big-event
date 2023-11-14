package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.service.ArticleService;
import com.itheima.utils.PageBean;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer user = (Integer) claims.get("id");
        article.setCreateUser(user);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        PageBean<Article> pageBean = new PageBean<>();
        //开始分页
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        //pageHelper自动分页不用传pageNum和pageSize
        List<Article> list = articleMapper.list(userId,categoryId,state);
        //使用page接收返回的数据
        Page<Article> page = (Page<Article>) list;
        pageBean.setTotal(page.getTotal());
        pageBean.setItem(page.getResult());
        return pageBean;
    }

    @Override
    public Article getById(Integer id) {
        return articleMapper.getById(id);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }
}
