import com.alibaba.fastjson.JSONObject;
import com.nanyan.dao.ChartDao;
import com.nanyan.service.ChartService;
import com.nanyan.utils.MailUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void test() throws MessagingException, IOException {
        String path = "src/main/webapp/statics/layui/api/init-user.json";
        String s = FileUtils.readFileToString(new File(path), "utf-8");
        System.out.println(JSONObject.parseObject(s));
//        System.out.println(s);
    }
}
