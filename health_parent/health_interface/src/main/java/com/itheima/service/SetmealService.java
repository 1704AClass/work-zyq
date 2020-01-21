package com.itheima.service;

import java.util.List;
import java.util.Map;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;

public interface SetmealService {

	void add(Setmeal setmeal, Integer[] checkgroupIds);

	PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

	List<Setmeal> findAll();

	Setmeal findById(int id);

}
