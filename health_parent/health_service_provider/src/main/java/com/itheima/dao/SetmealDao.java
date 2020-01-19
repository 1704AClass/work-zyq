package com.itheima.dao;

import java.util.Map;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

public interface SetmealDao {

	void setSetmealAndCheckGroup(Map<String, Integer> map);

	void add(Setmeal setmeal);

	Page<Setmeal> selectByCondition(String queryString);
	
}
