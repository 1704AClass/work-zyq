package com.itheima.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

public interface CheckGroupDao {

	void add(CheckGroup checkGroup);

	void setCheckGroupAndCheckItem(Map<String, Integer> map);

	Page<CheckItem> selectByCondition(String queryString);

	CheckGroup findById(Integer id);

	List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

	void deleteAssociation(Integer id);
}
