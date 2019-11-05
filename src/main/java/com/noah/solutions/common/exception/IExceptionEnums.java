package com.noah.solutions.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

public interface IExceptionEnums {
    Integer getCode();

    String getMsg();

    JSONObject getJSON();

    JSONObject getJSON(Object data);

    JSONObject pageData(Page page);

}
