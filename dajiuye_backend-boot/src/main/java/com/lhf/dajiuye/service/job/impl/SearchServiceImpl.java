package com.lhf.dajiuye.service.job.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
//import com.lhf.dajiuye.api.bean.Params;
//import com.lhf.dajiuye.api.bean.job.Job;
//import com.lhf.dajiuye.api.service.job.SearchService;
//import com.lhf.dajiuye.job.service.constants.QuestionConstants;
import com.lhf.dajiuye.bean.Params;
import com.lhf.dajiuye.bean.job.Job;
import com.lhf.dajiuye.constants.QuestionConstants;
import com.lhf.dajiuye.service.job.SearchService;
import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.MultiMatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 批量插入文档
     *
     * @param entities
     */
    @Override
    public void insert(List<Job> entities) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Job entity : entities) {
            bulkRequest.add(new IndexRequest(QuestionConstants.INDEX_JOB)
                    .id(entity.getJobId())
                    .source(JSON.toJSONString(entity), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        return;
    }

    @Override
    public PageInfo<Job> search(Params param) {
        SearchRequest request = buildSearchRequest(param);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            PageInfo<Job> pageInfo = buildSearchResult(response);
            pageInfo.setPages((int) (pageInfo.getTotal()%param.getPageSize()==0?
                                pageInfo.getTotal()/param.getPageSize():
                                pageInfo.getTotal()/param.getPageSize()+1));
            pageInfo.setPageNum(param.getPageNum());
            return pageInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PageInfo<Job> buildSearchResult(SearchResponse response) {
        SearchHits hits = response.getHits();
        List<Job> collect = Arrays.stream(hits.getHits()).map(hit -> {
            String sourceAsString = hit.getSourceAsString();
            Job job = JSON.parseObject(sourceAsString, Job.class);
            HighlightField jobName = hit.getHighlightFields().get("jobName");
            if (jobName != null) {
                String s = jobName.getFragments()[0].toString();
                job.setJobName(s);
            }
            HighlightField jobDetails = hit.getHighlightFields().get("jobDetails");
            if (jobDetails != null) {
                String s = jobDetails.getFragments()[0].toString();
                job.setJobDetails(s);
            }
            return job;
        }).collect(Collectors.toList());

        PageInfo<Job> pageInfo = new PageInfo<>(collect);
        return pageInfo;
    }

    /**
     * 构建查询请求
     * @param param
     * @return
     */
    private SearchRequest buildSearchRequest(Params param) {
        SearchRequest request = new SearchRequest();
        // 1.指定索引
        request.indices(QuestionConstants.INDEX_JOB);
        // 2.构建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 2.1构建bool查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        String query= param.getQuery();
        if(!StringUtils.isEmpty(param.getQuery())){
            // 效果一样，多字段查询性能差点
            MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.
                    multiMatchQuery(query,"jobName",  query, "jobDetails", query, "comFullName",query, "comIndustry");
//            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("all", keyword);
            boolQuery.must(matchQueryBuilder);
        }else {
            // 没有则匹配全部
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        // 薪资过滤
        if(StringUtils.hasText(param.getJobSalary())){
            boolQuery.filter(QueryBuilders.termQuery("jobSalary",param.getJobSalary()));
        }

        // 经验过滤
        if(StringUtils.hasText(param.getJobAge())){
            boolQuery.filter(QueryBuilders.termQuery("jobAge",param.getJobAge()));
        }

        // 类型过滤
        if(param.getJobType()!=-1){
            boolQuery.filter(QueryBuilders.termQuery("jobType",param.getJobType()));
        }

        // 第一部分布尔结束
        builder.query(boolQuery);

        // 2.2构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("jobName").field("jobDetails").field("comFullName").field("comIndustry")
                .preTags("<b style='color:red'>").postTags("</b>");
        builder.highlighter(highlightBuilder);

        // 分页
        builder.from(param.getPageNum()).size(param.getPageSize());

        request.source(builder);
        return request;
    }
}
