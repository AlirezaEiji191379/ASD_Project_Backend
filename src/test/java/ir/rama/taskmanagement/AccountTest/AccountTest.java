package ir.rama.taskmanagement.AccountTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class AccountTest {

    @Test
    public void FakeTest() {
        boolean a = true;
        Assert.isTrue(a, "invalid test");
    }

}
