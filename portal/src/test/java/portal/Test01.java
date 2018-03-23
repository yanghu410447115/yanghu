package portal;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**  
 * ClassName:Test01 <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午8:20:36 <br/>       
 */
public class Test01 {
    
    @Test
    public void test01(){
        String string = RandomStringUtils.randomNumeric(6);
        System.out.println(string);
    }
}
  
