import com.example.application.backend.Pressure;
import org.junit.Assert;
import org.junit.Test;

public class PressureTest {
    @Test
    //Tests the pressure is entered correctly in the different methods (right, left,under)
    public void test_getVals(){
        double tempData[] = {1.0,1.1,1.2};
        Pressure p1=new Pressure();
        p1.getVals(tempData);
        Assert.assertEquals(1.0,p1.getpLeft(),0.0);
        Assert.assertEquals(1.1,p1.getpRight(),0.0);
        Assert.assertEquals(1.2,p1.getpUnder(),0.0);
    }
}
