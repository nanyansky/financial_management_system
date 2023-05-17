import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.nanyan.dao.ChartDao;
import com.nanyan.dao.UserDao;
import com.nanyan.entity.User;
import com.nanyan.service.ChartService;
import com.nanyan.service.StockService;
import com.nanyan.service.UserService;
import com.nanyan.utils.MailUtil;
import com.nanyan.utils.StockDate;
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
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    //处理时间转JSON串问题
    private static SerializeConfig serializeConfig = new SerializeConfig();
    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        serializeConfig.put(Timestamp.class, new SimpleDateFormatSerializer(dateFormat));
    }

    @Autowired
    ChartDao chartDao;

    @Autowired
    ChartService chartService;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    StockService stockService;

    @Autowired
    UserDao userDao;

    @Test
    public void test() {
        JSONObject stockIndustry = stockService.getStockIndustry();
        System.out.println(stockIndustry);
    }
}
