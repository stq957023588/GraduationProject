package normalTest;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class test1 {

    public static void main(String [] args) throws IOException {
        test4();
    }
    @Test
    public void redisAllKey(){

    }

    public static void test(){
    }

    public void test2(){
        test();
    }

    public static void test3(){
        System.out.println(System.currentTimeMillis());
    }

    public static void test4( ) throws IOException {
        Resource resource = new ClassPathResource("/static/json");
        System.out.println(resource.getFile().getPath());

        File file=new File("classpath:static/json/continent1.json");
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        Map<String,String> map=new HashMap<>();
        map.put("ddd","ggg");
        try {
            bos.write(JSON.toJSONString(map).getBytes());
            bos.flush();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
