package com.itheima.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.QiniuUtils;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

import redis.clients.jedis.JedisPool;

/**
 * 套餐管理
 * @author 86181
 *
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Reference
	private SetmealService setmealService;
	@Resource
	private JedisPool jedisPool;
	
	//图片上传 
	@RequestMapping("/upload") 
	public Result upload(@RequestParam("imgFile")MultipartFile imgFile){ 
		System.out.println(imgFile);
		try{
			//获取原始文件名 
			String originalFilename = imgFile.getOriginalFilename(); 
			System.out.println(originalFilename);
			int lastIndexOf = originalFilename.lastIndexOf("."); 
			System.out.println(lastIndexOf);
			//获取文件后缀
			String suffix = originalFilename.substring(lastIndexOf - 1);
			//使用UUID随机产生文件名称，防止同名文件覆盖 
			String fileName = UUID.randomUUID().toString() + suffix; 
			QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
			//图片上传成功
			Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS); 
			result.setData(fileName); 
			//jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName );
			return result; 
		}catch (Exception e){ 
			e.printStackTrace(); 
			//图片上传失败 
			return new Result(false,MessageConstant.PIC_UPLOAD_FAIL); 
		}
	}
	//新增 
	@RequestMapping("/add") 
	public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){ 
		System.out.println(setmeal);
		try { 
			setmealService.add(setmeal,checkgroupIds); 
			}catch (Exception e){ 
				//新增套餐失败 
				return new Result(false,MessageConstant.ADD_SETMEAL_FAIL); 
			}
		//新增套餐成功 
		return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS); 
	}
	//分页查询 
	@RequestMapping("/findPage") 
	public PageResult findPage(@RequestBody QueryPageBean queryPageBean){ 
		System.out.println("aa");
		PageResult pageResult = setmealService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
		return pageResult; 
	}
}
