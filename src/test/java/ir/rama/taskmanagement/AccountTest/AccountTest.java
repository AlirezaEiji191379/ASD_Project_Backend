package ir.rama.taskmanagement.AccountTest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTest {

    @Test void FakeTest(){
        //System.out.println("fake test was tested");
        boolean a = true;
        Assert.isTrue(a, "sampletest");
    }

}
