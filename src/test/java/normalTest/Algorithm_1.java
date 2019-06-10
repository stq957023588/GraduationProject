package normalTest;

import com.slz.demo.utils.Tools;
import org.junit.Test;

import java.util.*;

public class Algorithm_1 {

    @Test
    public void test(){
        Tools.getListContent(cal(88, Arrays.asList(20,99,44,23,65,2,70,18,44,80,12,34,25)));
    }


    public List<Integer> cal(int target,List<Integer> list){
        Map<Integer,Integer> map=new HashMap<>();
        List<Integer> result=new ArrayList<>();

        for (Integer i :
                list) {
            if(map.size()==0){
                map.put(target-i,i);
            }else {
                if (map.get(i)!=null){
                    result.add(i);
                    result.add(map.get(i));
                }else {
                    map.put(target-i,i);
                }
            }
        }
        return result;
    }
}
