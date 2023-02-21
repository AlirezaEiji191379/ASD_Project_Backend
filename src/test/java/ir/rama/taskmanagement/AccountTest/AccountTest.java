package ir.rama.taskmanagement.AccountTest;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class AccountTest {

    @Test void FakeTest(){
        System.out.println("fake test was tested");
        boolean a = true;
        Assert.isTrue(a, "sampletest");
    }

}
