package normalTest;

import com.slz.demo.pojo.User;
import com.slz.demo.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class RemoveRepeatTest {

    public static void main(String[] args){

        List<User> list=new ArrayList<>();

        User user1=new User();
        user1.setId(11);
        list.add(user1);
        User user2=new User();
        user2.setId(11);
        user2.setPassword("ddd");
        list.add(user2);
        User user3=new User();
        user3.setId(22);
        list.add(user3);
        list.add(user3);
        Tools.getListContent(list);
        System.out.println("---------");
        Tools.RemoveRepeat(list);

        Tools.getListContent(list);

    }


}
