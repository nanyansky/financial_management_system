import com.nanyan.dao.ChartDao;
import com.nanyan.service.ChartService;
import com.nanyan.utils.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
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
    public void test() throws MessagingException {
//        System.out.println(chartDao.getIncomeTypeData());
//        System.out.println(chartService.getIncomeTypeData());
        List<String> list = new ArrayList<>();
        list.add("2826407926@qq.com");
        list.add("gaoqidong0@gmail.com");
//        list.add("csbjcscb@qq.com");

        MailUtil.sendMail(list,"test");
    }
}
