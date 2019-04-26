package com.cxg.weChat.core.utils;

import java.io.Serializable;
import java.util.List;

/**
* @Description:    分页工具类
* @Author:         Cheney Master
* @CreateDate:     2018/7/30 9:22
* @Version:        1.0
*/

public class  PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private List<?> rows;

	public PageUtils(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
