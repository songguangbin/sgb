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
			//������ĺ���
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			//������Ա�
			Random random = new Random();
			String sex = random.nextBoolean()?"��":"Ů";
			user.setSex(sex);
			//������ֻ���
			String phone ="13"+ StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			//���������
			int random2 = (int) (Math.random()*20);
			int len=random2<3?random2+3:random2;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			//���������18-70
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			ulist.add(user);
		}
		/*System.out.println("jdk�����л���ʽ");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");*/
		System.out.println("json�����л���ʽ");
		/*long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		boundListOps.leftPush(ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");*/
		System.out.println("hash�����л���ʽ");
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		long start = System.currentTimeMillis();
		boundHashOps.put("hash", ulist);
		long end = System.currentTimeMillis();
		System.out.println("��ʱ:"+(end-start)+"����");
	}
	

}
