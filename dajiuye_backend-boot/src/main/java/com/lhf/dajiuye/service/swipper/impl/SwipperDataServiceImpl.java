package com.lhf.dajiuye.service.swipper.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.PageParam;
import com.lhf.dajiuye.bean.swipper.Swiper;
import com.lhf.dajiuye.mapper.swipper.SwiperDataMapper;
import com.lhf.dajiuye.service.swipper.SwipperDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SwipperDataServiceImpl extends ServiceImpl<SwiperDataMapper, Swiper> implements SwipperDataService {

    @Autowired
    private SwiperDataMapper mapper;

    /**
     * 获取轮播图数据
     *
     * @return
     * @throws IOException
     */
    @Override
    public PageInfo<Swiper> getSwiperDataList(PageParam param) {
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        // threadLocal<Page>中设置分页对象Page
        PageHelper.startPage(pageNum, pageSize);

        // 本质是一个Page对象
        List<Swiper> list = list();

        int pages = list.size();

        if (list instanceof Page) {
            Page page = (Page) list;
            pages = page.getPages(); // 数据库真正的总页数
        }

        PageInfo<Swiper> pageInfo = new PageInfo<>(list);
        pageInfo.setPages(pages); // 设置真正的总页数

        return pageInfo;
    }
}
