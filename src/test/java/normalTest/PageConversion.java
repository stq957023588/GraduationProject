package normalTest;

import org.junit.Test;

public class PageConversion {


    @Test
    public void test(){
        conversion(2,8);
    }
    public void conversion(int page,int size){
        int start=1+size*(page-1);
        int end=size*page;


        System.out.println("start:"+start+"\tend:"+end);
    }

}
