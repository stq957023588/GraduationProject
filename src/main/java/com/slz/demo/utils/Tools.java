package com.slz.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slz.demo.enums.PathEnum;
import com.slz.demo.pojo.Strategy;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public  class Tools<T> {
    private static int picCount;
    private static final String [] addressLevel={"continent","country","province","city"};


    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    //生成时间戳
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    public static Map<String ,String > doGet(String url){
        String result="";
        BufferedReader in;
        try{
            URL realUrl=new URL(url);
            URLConnection connection=realUrl.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(7000);
            connection.connect();

            in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line=in.readLine())!=null){
                result+=line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return (Map)JSON.parse(result);
    }


    //去重
    public static void RemoveRepeat(List list){
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }

    //打印HttpServletRequest中的各个常用方法
    public static void printRequest(HttpServletRequest request){
        System.out.println("ContextPath:\t"+request.getContextPath());
        System.out.println("ServletPath:\t"+request.getServletPath());
        System.out.println("RequestUrl:\t"+request.getRequestURI());
        System.out.println("Header:\t"+request.getHeaderNames());
        System.out.println("RemoteAddr:\t"+request.getRemoteAddr());
        System.out.println("RemoteHost:\t"+request.getRemoteHost());

    }


    //二进制转换成字符串
    public static String toString(String binary) {
        String[] tempStr=binary.split(" ");
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
            tempChar[i]=BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }


    //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }


    //将二进制转换成字符
    public static char BinstrToChar(String binStr){
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }
        return (char)sum;
    }



    /**
     * 获取ip地址
     * @param request request请求
     * @return
     */
    public static String  getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 获取不重复的ID
     * @param prefix ID前缀
     * @return
     */
    public static String getId(String prefix){
        return prefix+UUID.randomUUID();
    }

    /**
     * 将数组转换成map集合
     * @param info
     * @return
     */
    public static Map<String ,String> toCityMap(String[] info){
        Map<String ,String > map=new HashMap<>();
        for(int i=0;i<info.length;i++){
            map.put(addressLevel[i],info[i]);
        }
        return map;
    }

    /**
     * 遍历集合
     * @param list
     */
    public static void getListContent(List list){
        for (Object o :
                list) {
            System.out.println("\t\t"+o);
        }
    }

    /**
     * 遍历数组
     * @param strs
     */
    public static void getDataCollectionContent(String [] strs){
        for (String string:strs){
            System.out.println(string);
        }
    }

    public static void traverseMap(Map<String ,List<String >> map){
        Set<String > set=map.keySet();
        for (String string:set){
            System.out.println(string+":");
            getListContent(map.get(string));
        }
    }



    /**
     * 计数
     * @return
     */
    public static synchronized int getPicCount(){
        picCount++;
        return picCount;
    }

    /**
     * 获取可在网页上访问本地图片的路径
     * @param fileName
     * @return
     */
    public static String toServerPath(String fileName){
        return PathEnum.ServerPathPrefix.getMessage()+fileName+PathEnum.jpg.getMessage();
    }

    /**
     *将inputstream转换成byte数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] stramToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static void writeJsonToResources(Map map,String path ){
        File file=null;
        FileOutputStream fos= null;
        try {
           file= ResourceUtils.getFile(path);
           fos = new FileOutputStream(file);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            bos.write(JSON.toJSONString(map).getBytes());
            bos.flush();
            bos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJsonToLocal(Map map,String path )  {
        File file=new File(path);
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos=new BufferedOutputStream(fos);

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
