package ir.rama.taskmanagement.AccountTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class AccountTest {

    @Test
    public void FakeTest(){
        //System.out.println("fake test was tested");
        boolean a = true;
        Assert.assertEquals(a,true);
    }

}
