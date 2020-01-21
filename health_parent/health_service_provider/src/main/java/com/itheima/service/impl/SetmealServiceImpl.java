package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.SetmealService;

import redis.clients.jedis.JedisPool;

@Service(interfaceClass = SetmealService.class) 
@Transactional
public class SetmealServiceImpl implements SetmealService {

	@Resource
	private SetmealDao setmealDao;
	@Resource 
	private JedisPool jedisPool;

	//新增套餐
	@Override
	public void add(Setmeal setmeal, Integer[] checkgroupIds) {
		// TODO Auto-generated method stub
		setmealDao.add(setmeal); 
		if(checkgroupIds != null && checkgroupIds.length > 0){ 
			//绑定套餐和检查组的多对多关系 
			setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds); 
		}
		//将图片名称保存到Redis 
		savePic2Redis(setmeal.getImg());
	}
	
	//将图片名称保存到Redis 
	private void savePic2Redis(String pic){ 
		jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic); 
	}
	
	//绑定套餐和检查组的多对多关系 
	private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) { 
		for (Integer checkgroupId : checkgroupIds) { 
			Map<String,Integer> map = new HashMap<>(); 
			map.put("setmeal_id",id); 
			map.put("checkgroup_id",checkgroupId); 
			setmealDao.setSetmealAndCheckGroup(map); 
		} 
	}

	//分页查询
	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage,pageSize); 
		Page<Setmeal> page = setmealDao.selectByCondition(queryString); 
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public List<Setmeal> findAll() {
		// TODO Auto-generated method stub
		return setmealDao.findAll();
	}

	@Override
	public Setmeal findById(int id) {
		// TODO Auto-generated method stub
		return setmealDao.findById(id);
	}
}
