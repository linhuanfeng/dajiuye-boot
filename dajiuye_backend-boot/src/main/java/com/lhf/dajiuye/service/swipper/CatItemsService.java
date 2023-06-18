package com.lhf.dajiuye.service.swipper;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.PageParam;
import com.lhf.dajiuye.bean.swipper.CatItems;


public interface CatItemsService extends IService<CatItems> {
    PageInfo<CatItems> getCatItemsList(PageParam param);
}
