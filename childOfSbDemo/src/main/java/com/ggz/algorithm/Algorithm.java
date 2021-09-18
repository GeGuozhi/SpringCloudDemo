package com.ggz.algorithm;

import com.ggz.childMavenProject.MonitorTestDriver;
import com.ggz.childMavenProject.Student;
import com.ggz.childMavenProject.Teacher;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

import java.io.*;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Algorithm {

    public static void reflect(Object object,Object object2){
        Class cls = object.getClass();
        Class clsb = object2.getClass();
        //将参数类转换为对应属性数量的Field类型数组（即该类有多少个属性字段 N 转换后的数组长度即为 N）
        Field[] fields = cls.getDeclaredFields();
        Field[] fields2 = clsb.getDeclaredFields();
        for(int i = 0;i < fields.length; i ++){
            Field f = fields[i];
            Field f2 = fields2[i];
            f.setAccessible(true);
            f2.setAccessible(true);
            try {
                if(f.get(object) != null){
                    f2.set(object2,f.get(object));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 原子性测试
     */
    public static volatile int race = 0;

    public static int normalInt = 0;

    private static final int THREADS_COUNT = 20;

    public static void increase() {
        race++;
        atomicInteger.getAndIncrement();
        normalInt++;
        atomicInteger1.getAndIncrement();
    }


    /**
     * 可重用锁demo
     * tryLock 可以增加时间参数
     * lock次数必须和unlock次数相等否则报错。
     */
    private Lock lock = new ReentrantLock();

    public void method(Thread thread) throws InterruptedException {
//        lock.lock();
        if (lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                System.out.println("线程名：" + thread.getName() + "获得了锁");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程名：" + thread.getName() + "释放了锁");
                lock.unlock();
            }
        }
    }


    /**
     * 原子操作类
     * atomicInteger.incrementAndGet()可以原子操作int类型。
     * atomicInteger.get()可以获取到int类型的当前值。
     */
    static AtomicInteger atomicInteger = new AtomicInteger();

    static AtomicInteger atomicInteger1 = new AtomicInteger();

    public void testAtom(Long time) {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    atomicInteger.incrementAndGet();
                    if (atomicInteger.get() == 50) {
                        System.out.println((System.currentTimeMillis() - time) / 1000 + "s");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }
    }
    protected void testProtect(){
        System.out.println("protect");
    }

    /**
     * 引用测试。
     *
     * @return
     */
    public static Teacher newTeacher() {
        Teacher teacher = new Teacher();
        teacher.setAge(1);
        teacher.setName("teacher1");
        return teacher;
    }

    /**
     * StringBuffer测试。
     *
     * @param a
     * @param b
     */
    public static void change(StringBuffer a, StringBuffer b) {
        a = b;
        b.append(a);
    }

    /**
     * 序列化 一个对象。
     * ObjectOutputStream.writeObject(Object) 写入一个对象到输出流中，可以使用
     * ObjectInputStream.readObjct(byte[] byte)反序输出此对象。
     * <p>
     * 深层拷贝，需要序列化每个对象。
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialByte(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    /**
     * 反序列化一个对象。
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialByte(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return ois.readObject();
    }

    public void setHash(Map map) {
        map.put("key", "key");
    }

    public static void testt(Student stu1) {
        stu1.setName("静态设置姓名");
    }

    public void setName(Student stu) {
        stu.setName("非静态设置姓名");
    }

    public void exchange(int a, int b) {
        int c = a;
        a = b;
        b = c;
    }

    /**
     * 监听者模式发布事件。Simple demo
     */
    public void listenerMode() {
//        EventSource eventSource = new EventSource();
//        MonitorListener a = new AListener();
//        MonitorListener b = new BListener();
//        MonitorListener c = new CListener();
//        eventSource.addListener(a);
//        eventSource.addListener(b);
//        eventSource.addListener(c);
//
//        /**
//         * PrintEvent为发布的事件，可以携带任何属性。
//         */
//        eventSource.notifyAllListener(new PrintEvent("openWindows"));
    }

    /**
     * 测试排序后循环判断条件，花费时间问题，排序后花费的时间更短。
     */
    public void testLoopCostTime() {
        int[] arrays2 = new int[99999999];
        int flag2 = 0;
        while (flag2 < arrays2.length) {
            arrays2[flag2] = (int) (new Double(Math.random() * (arrays2.length)).longValue());
            flag2++;
        }
        Long time = System.currentTimeMillis();
        int c = 0;
        for (int b = 0; b < arrays2.length; b++) {
            if (b > (arrays2.length / 2)) {
                c++;
            }
        }
        System.out.println("排序前:" + (System.currentTimeMillis() - time));

        Arrays.sort(arrays2);
        Long time2 = System.currentTimeMillis();
        int d = 0;
        for (int b = 0; b < arrays2.length; b++) {
            if (b > (arrays2.length / 2)) {
                d++;
            }
        }
        System.out.println("排序后:" + (System.currentTimeMillis() - time2));
    }

    /**
     * 测试guava（瓜va）loadCache缓存
     */
    public void guavaLoadCache() throws ExecutionException {
        LoadingCache<String, String> cache2 = CacheBuilder.newBuilder()
                .maximumWeight(1000)
                .weigher(new Weigher<String, String>() {
                    @Override
                    public int weigh(String key, String value) {
                        return key.length();
                    }
                })
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key + "cache";
                    }
                });
        cache2.put("test", "23333");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache2.get("test"));
        System.out.println(cache2.get("scj"));
    }

    /**
     * continue直接退出当次循环，break退出当前循环
     */
    public void loopContinueKey() {
        for (int i = 0; i < 100; i++) {
            if (i != 5) {
                continue;
            } else {
                System.out.println(i);
                break;
            }

        }
    }

    public void ThreadSleepUtils(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 巨大数加法器计算，不支持负数，需要特殊处理
     * @param a
     * @param b
     * @return
     */
    public static String add(String a,String b){
        //求和后的数字的倒序，反向输出就可以了,先补全前面的0，让a,b同位
        int len = a.length()>=b.length()?a.length()-b.length():b.length()-a.length();

        if(a.length()>=b.length()){
            for(int i = 0 ; i < len ; i++){
                b = "0".concat(b);
            }
        }else{
            for(int i = 0 ; i< len ; i++){
                a = "0".concat(a);
            }
        }

        StringBuffer c = new StringBuffer();
        //进位
        int extra = 0;
        int sum = 0;
        // a = 9999 b = 9999 len = 4
        for(int i = 0 ; i <a.length() ; i++){
            sum = Integer.valueOf(String.valueOf(a.charAt(a.length()-i-1))) + Integer.valueOf(String.valueOf(b.charAt(a.length()-i-1))) + extra;
            extra = sum>=10?1:0;
            if(i<a.length()-1){
                c.append(sum>=10?sum-10:sum);
            }else{
                c.append(new StringBuffer(String.valueOf(sum)).reverse());
            }
            sum = 0;
        }
        return c.reverse().toString();
    }

    /**
     * 大数减法，不支持负数，需要特殊处理
     * @param a
     * @param b
     * @return
     */
    public static String subtract(String a,String b){
        if(a.equals(b)){
            return "0";
        }
        boolean flag = max(a,b);
        //求和后的数字的倒序，反向输出就可以了,先补全前面的0，让a,b同位
        int len = a.length()>=b.length()?a.length()-b.length():b.length()-a.length();

        if(a.length()>=b.length()){
            for(int i = 0 ; i < len ; i++){
                b = "0".concat(b);
            }
        }else{
            for(int i = 0 ; i< len ; i++){
                a = "0".concat(a);
            }
            String c = a;
            a = b;
            b = c;
        }

        StringBuffer c = new StringBuffer();
        //进位
        int extra = 0;
        int sum = 0;
        int len1 = a.length();
        // a = 001234 b = 999999 len1 = 4 maxFlag = true;
        for(int i = 0 ; i <len1 ; i++){
            sum = Integer.valueOf(String.valueOf(a.charAt(a.length()-i-1))) - Integer.valueOf(String.valueOf(b.charAt(a.length()-i-1))) + extra;
            extra = sum<0?-1:0;
            if(i<a.length()-1){
                c.append(sum < 0?(sum+10):sum);
            }else{
                c.append(sum == 0?"":sum);
            }
            sum = 0;
        }
        //根据大小决定是否增加负号
        return flag?c.reverse().toString():"-".concat(c.reverse().toString());
    }

    /**
     * 判断a和b的绝对值哪个大,决定是谁减谁
     * @param a
     * @param b
     * @return
     */
    public static boolean max(String a,String b){
        if(a.length()>b.length()){
            return true;
        }else if(a.length() == b.length()){
            for(int i = 0 ; i< a.length() ; i++){
                if(a.charAt(a.length()-i-1)>b.charAt(b.length()-i-1)){
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }


    /**
     * stream的各种用法
     */
    public void testStream(){
        //        Arrays.asList(new Student(1), new Student(2), new Student(3)).
//                stream().
//                filter(entity -> entity.getAge() != 2).
//                forEach(S -> System.out.println(S.getAge()));

//        String[] array = { "大雄，男", "静香，女", "胖虎，男" };
//        Consumer<String> a = s -> System.out.print("测试Consumer");
//        Consumer<String> b = s -> System.out.println("测试Consumer，姓名："+s.split(",")[0]);
//        a.andThen(b).accept("大雄，男");


        List<Student> list = Arrays.asList(new Student(1), new Student(2),new Student(1),new Student(1));
        //a
//        Map<Integer,List<Integer>> map = list.
//                stream().
//                collect(Collectors.groupingBy(Student::getAge, Collectors.mapping(Student::getAge, Collectors.toList())));
//b
        //Map<Integer, List<Student>> map1 =
        list.
                stream().
                collect(Collectors.groupingBy(s->s.getAge())).
                forEach((key,value)->{
                    value.forEach(s -> System.out.println("value:"+s+",key:"+key+",value:"+s.getAge()));
                });
        List<Student> list1 = Arrays.asList(new Student(1), new Student(4),new Student(2),new Student(5));


        list1.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList()).forEach(student -> System.out.println(student.getAge()));
    }

    /**
     * 使用properties类加载属性文件
     */
    public void inputStreamTest(){
        InputStream in = MonitorTestDriver.class.getClassLoader().getResourceAsStream("application.yml");
        InputStream in2 = MonitorTestDriver.class.getClassLoader().getResourceAsStream("sysConfig.properties");
        Properties pro = new Properties();
        Properties pro1 = new Properties();
        try {
            pro.load(new InputStreamReader(in));
            pro1.load(new InputStreamReader(in2));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        pro1.stringPropertyNames().stream().forEach(a-> System.out.println(a+":"+pro1.getProperty(a)));
        pro1.setProperty("ff","嗨啊");
        System.out.println(MessageFormat.format("nnnnn{0},{1}","one","two"));
        System.out.println(pro1.getProperty("ff"));
        System.out.println(pro1.getProperty("path"));

        try {
            in.close();
            in2.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
