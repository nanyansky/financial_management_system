import com.nanyan.dao.ChartDao;

/**
 * @author nanyan
 * @version 1.0
 * @description: TODO
 * @date 2023/4/6 21:40
 */
public class test {
    public static void main(String[] args) {
        ChartDao chartDao = new ChartDao();
        System.out.println(chartDao.getIncomeCount());
    }
}
