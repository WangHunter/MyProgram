import com.wonder.service.RecommandDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/8/21.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-rpc-test.xml"})
public class TestRecommandSystemProject {

    @Autowired
    private RecommandDataService recommandDataService;
    @Test
    public  void run() {
        recommandDataService.translateData("123#kk#-1");
    }
}
