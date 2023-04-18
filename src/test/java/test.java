import com.alibaba.fastjson.JSONObject;
import com.nanyan.dao.ChartDao;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.ChartService;
import com.nanyan.service.UserService;
import com.nanyan.utils.MailUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 21:40
 */

//注解引用SpringJUnit这个
@RunWith(SpringJUnit4ClassRunner.class)
//注解Contest寻找配置文件
@ContextConfiguration("classpath:spring.xml")
public class test {

    @Autowired
    ChartDao chartDao;

    @Autowired
    ChartService chartService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserDao userDao;

    @Test
    public void test() {

        User user = userDao.findByUserName("admin");

        String key = "user:" + user.getUserName();

        stringRedisTemplate.opsForValue().set(key,JSONObject.toJSONString(user));

        System.out.println(stringRedisTemplate.opsForValue().get(key));
    }
}
