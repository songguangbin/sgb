package com.sgb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import com.sgb.bean.User;
import com.sgb.utils.StringUtils;


@SuppressWarnings("restriction")

public class WeekTest extends BaseTest{
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void testDate(){
		List<User> ulist=new ArrayList<User>();
		for (int i = 0; i <100000; i++) {
			User user = new User();
			user.setId(i+1);
			//随机中文汉字
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			//随机的性别
			Random random = new Random();
			String sex = random.nextBoolean()?"男":"女";
			user.setSex(sex);
			//随机的手机号
			String phone ="13"+ StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			//随机的邮箱
			int random2 = (int) (Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			//随机的生日18-70
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			ulist.add(user);
		}
		/*System.out.println("jdk的序列化方式");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		System.out.println("json的序列化方式");
		/*long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");*/
		System.out.println("hash的序列化方式");
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		long start = System.currentTimeMillis();
		boundHashOps.put("hash", ulist);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)+"毫秒");
	}
	

}
