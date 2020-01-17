package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
/*** 检查组服务 */ 
@Service(interfaceClass = CheckGroupService.class) 
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

	@Resource
	private CheckGroupDao checkGroupDao;
	
	@Override
	public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
		// TODO Auto-generated method stub
		checkGroupDao.add(checkGroup); 
		setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
	}
	
	
	//向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系） 
	public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){ 
		if(checkitemIds != null && checkitemIds.length > 0){ 
			for (Integer checkitemId : checkitemIds) { 
				Map<String,Integer> map = new HashMap<>(); 
				map.put("checkgroup_id",checkGroupId); 
				map.put("checkitem_id",checkitemId); 
				checkGroupDao.setCheckGroupAndCheckItem(map); 
			} 
		} 
	}
	//设置检查组合和检查项的关联关系 
	/*public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){ 
		if(checkitemIds != null && checkitemIds.length > 0){ 
			for (Integer checkitemId : checkitemIds) { 
				Map<String,Integer> map = new HashMap<>(); 
				map.put("checkgroup_id",checkGroupId); 
				map.put("checkitem_id",checkitemId); 
				checkGroupDao.setCheckGroupAndCheckItem(map); 
				} 
			} 
		}*/

	@Override
	public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
		// TODO Auto-generated method stub
		PageHelper.startPage(currentPage,pageSize); 
		Page<CheckItem> page = checkGroupDao.selectByCondition(queryString); 
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override
	public CheckGroup findById(Integer id) {
		// TODO Auto-generated method stub
		return checkGroupDao.findById(id);
	}

	@Override
	public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
		// TODO Auto-generated method stub
		return  checkGroupDao.findCheckItemIdsByCheckGroupId(id);
	}

	@Override
	public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
		// TODO Auto-generated method stub
		//根据检查组id删除中间表数据（清理原有关联关系）
		checkGroupDao.deleteAssociation(checkGroup.getId()); 
		//向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
		setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds); 
		//更新检查组基本信息 checkGroupDao.edit(checkGroup);
	}
}
