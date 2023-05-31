package com.lhf.dajiuye.service.job;

import com.github.pagehelper.PageInfo;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
//import com.lhf.dajiuye.api.bean.Params;
//import com.lhf.dajiuye.api.bean.job.Job;

import java.io.IOException;
import java.util.List;

public interface SearchService  {
    /**
     * 根据关键字搜索酒店信息
     * @return 酒店文档列表
     */
//    PageResult search(RequestParams params);

//    Map<String, List<String>> getFilters(RequestParams params);

//    List<String> getSuggestions(String prefix);

//    void createIndex(String indexName,String mappingTemplate) throws IOException;

//    void deleteHotelIndex() throws IOException;

    void insert(List<Job> questionEntities) throws IOException;
//    void insertUserRecords(List<UserRecordEntity> entities) throws IOException;

    PageInfo<Job> search(Params param);
//    void search2(SearchParam param);

//    List<String> hotWords();

//    void saveHotWord(SearchParam searchParam) throws IOException;

//    List<String> getSuggestions(String prefix) throws IOException;
}
