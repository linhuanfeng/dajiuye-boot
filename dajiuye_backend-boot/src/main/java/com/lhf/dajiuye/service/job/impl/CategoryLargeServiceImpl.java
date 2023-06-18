package com.lhf.dajiuye.service.job.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhf.dajiuye.bean.Cascade;
import com.lhf.dajiuye.bean.job.CategoryLarge;
import com.lhf.dajiuye.bean.job.CategoryMiddle;
import com.lhf.dajiuye.bean.job.CategorySmall;
import com.lhf.dajiuye.mapper.job.CategoryLargeMapper;
import com.lhf.dajiuye.service.job.CategoryLargeService;
import com.lhf.dajiuye.service.job.CategoryMiddleService;
import com.lhf.dajiuye.service.job.CategorySmallService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryLargeServiceImpl extends ServiceImpl<CategoryLargeMapper, CategoryLarge> implements CategoryLargeService {
    @Resource
    private CategoryMiddleService categoryMiddleService;
    @Resource
    private CategorySmallService categorySmallService;

    @Override
    public List<CategoryLarge> getFullCategoryDataList() {
        List<CategoryLarge> lCatList = list();
        List<CategoryMiddle> mCatDataList = categoryMiddleService.list();
        List<CategorySmall> sCatDataList = categorySmallService.list();

        for (CategorySmall sCat : sCatDataList) {
            // 因为默认升序排列
            int n = sCat.getMId() - 1;
            if (mCatDataList.get(n).getChildren() == null) {
                mCatDataList.get(n).setChildren(new ArrayList<CategorySmall>());
            }
            mCatDataList.get(n).getChildren().add(sCat);
        }

        for (CategoryMiddle mCat : mCatDataList) {
            int n = mCat.getLId() - 1;
            if (lCatList.get(n).getChildren() == null) {
                lCatList.get(n).setChildren(new ArrayList<CategoryMiddle>());
            }
            lCatList.get(n).getChildren().add(mCat);
        }

        return lCatList;
    }

    /**
     * 将职位的树结构组织成级联的树结构
     * @return
     */
    @Override
    public List<Cascade> getFullCategoryDataListCascade() {
        List<CategoryLarge> categoryLarges = list();
        List<CategoryMiddle> categoryMiddles = categoryMiddleService.list();
        List<CategorySmall> categorySmalls = categorySmallService.list();

        List<Cascade> cascades = categoryLarges.stream().map(e -> {
            Cascade cascade = new Cascade();
            cascade.setId(e.getId());
            cascade.setName(e.getLName());
            return cascade;
        }).collect(Collectors.toList());

        for (Cascade c1 : cascades) {
            for (CategoryMiddle c2 : categoryMiddles) {
                if(c2.getLId().equals(c1.getId())){
                    if(c1.getChildren()==null){
                        c1.setChildren(new LinkedList<>());
                    }
                    Cascade c2Add = new Cascade();
                    c2Add.setId(c2.getId());
                    c2Add.setName(c2.getMName());
                    c1.getChildren().add(c2Add);
                }
            }
        }

        for (Cascade c1 : cascades) {
            List<Cascade> children = c1.getChildren();
            if(children!=null){
                for (Cascade c2 : children) {
                    for (CategorySmall c3 : categorySmalls) {
                        if(c3.getMId().equals(c2.getId())){
                            if(c2.getChildren()==null){
                                c2.setChildren(new LinkedList<>());
                            }
                            Cascade c3Add = new Cascade();
                            c3Add.setId(c3.getId());
                            c3Add.setName(c3.getSName());
                            c2.getChildren().add(c3Add);
                        }
                    }
                }
            }
        }
        return cascades;
    }
}
