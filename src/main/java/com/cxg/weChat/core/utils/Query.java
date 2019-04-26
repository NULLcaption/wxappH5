package com.cxg.weChat.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 数据库查询参数工具类
 * 注意这里的分页工具mysql和oracle是有区别的：
 * mysql:("limit", limit)
 * oracle:("limit", limit*(offset / limit + 1))
 * 为什么呢？
 * 因为两者在后端数据的分页方式完全不同导致在程序处理的时候需要处理分页的数据。
 * @Author xg.chen
 * @Date 15:39 2018/11/29
**/
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 
	private int offset;
	// 每页条数
	private int limit;

	public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.offset = Integer.parseInt(params.get("offset").toString());
		this.limit = Integer.parseInt(params.get("limit").toString());
		this.put("offset", offset);
		this.put("page", offset / limit + 1);
		this.put("limit", limit*(offset / limit + 1));
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
