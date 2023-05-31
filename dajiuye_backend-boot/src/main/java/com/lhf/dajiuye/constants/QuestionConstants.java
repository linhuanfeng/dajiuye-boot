package com.lhf.dajiuye.constants;

public interface QuestionConstants {
    String INDEX_JOB="job";
    String INDEX_NAME_USER_RECORD="user_record";
    String INDEX_NAME_HOT="hot";
    String MAPPING_HOT_TEMPLATE="{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"hotWord\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
