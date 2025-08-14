package com.ggz.configclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ggz.configclient.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MockitoSettings(strictness = Strictness.LENIENT)
class ConfigClientApplicationTests {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TestService testService;

    @Test
    void test() throws ExecutionException, InterruptedException {
//        when(testService.test(any(Integer.class))).thenReturn("TEST");
//        String result = testService.test(5);
//        assertEquals("TEST",result);
//
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        };
//
//        Consumer<String> consumer1 = System.out::println;
//        List<String> list = Arrays.asList("A","B","C");
//        list.stream().forEach(s -> {
//            System.out.println(s);
//        });
//
//        list.stream().forEach(consumer);
//
//        list.stream().forEach(consumer1);

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        String test = "TEST";
//        Supplier<String> supplier = new Supplier<String>() {
//            @Override
//            public String get() {
//                return test;
//            }
//        };
//        //supplyAsync有返回值，runAync没有返回值
//        CompletableFuture<String> task = CompletableFuture.supplyAsync(supplier, executorService);
//        System.out.println("supplyAsync返回结果:" + task.join());
//
//
//        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
//            System.out.print("runAsync 无返回");
//        }, executorService);
//
//        //获取返回值
//        System.out.println("获取CompletableFuture.get():" + task.get());
//        System.out.println("获取CompletableFuture.join():" + task.join());
//        //使用完毕后要关闭线程池
//        executorService.shutdown();
//
//

//        System.out.println("20230228:"+isValidDate("20230228"));
//        System.out.println("20230229:"+isValidDate("20230229"));
//        System.out.println("20230230:"+isValidDate("20230230"));
//        System.out.println("20240228:"+isValidDate("20240228"));
//        System.out.println("20240229:"+isValidDate("20240229"));
//        System.out.println("20240230:"+isValidDate("20240230"));
//        System.out.println("20240330:"+isValidDate("20240330"));
//        System.out.println("20240331:"+isValidDate("20240331"));
//        System.out.println("20240332:"+isValidDate("20240332"));
//        System.out.println("20240430:"+isValidDate("20240430"));
//        System.out.println("20240431:"+isValidDate("20240431"));
//        System.out.println("20240432:"+isValidDate("20240432"));

//        List<String> stringList = Arrays.asList("1", "2", "3", "4");
//        List<Integer> integerList = Arrays.asList("1", "2", "3", "4").stream().map(i -> Integer.valueOf(i)).collect(Collectors.toList());
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return false;
//            }
//        };
//
//        List<Integer> list2 = integerList.stream().map(this::testDoubleInteger).filter(i -> i > 1).collect(Collectors.toList());
//        Optional<String> optional = stringList.stream().findFirst();
//        if(optional.isPresent()){
//            System.out.println(optional.get());
//        }
//        Optional.ofNullable(null);
//        System.out.println(list2);
//        System.out.println("----");
//        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
//        //返回一个optional对象
//        Optional<Integer> first = stream.filter(i -> i > 4)
//                .findFirst();
//
//        //optional对象有需要Supplier接口的方法
//        //orElse，如果first中存在数，就返回这个数，如果不存在，就放回传入的数
//        System.out.println(first.orElse(1));
//        System.out.println(first.orElse(7));
//
//        Supplier<Integer> supplier = new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                //返回一个随机值
//                return new Random().nextInt();
//            }
//        };
//
//        //orElseGet，如果first中存在数，就返回这个数，如果不存在，就返回supplier返回的值
//        System.out.println(first.orElseGet(supplier));

//        List<Map<String,String>> list = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            Map map = new Hashtable();
//            map.put("ggz",String.valueOf(i));
//            map.put("test",String.valueOf(i));
//            list.add(map);
//        }
//
//        Map map = new Hashtable();
//        map.put("ggz","1");
//        list.add(map);
//
//        Map<String,Long> mapp = list.stream()
//                .flatMap(i->i.entrySet().stream())
//                .filter(i->"ggz".equals(i.getKey()))
//                .collect(Collectors.groupingBy(Map.Entry::getValue,Collectors.counting()));
//
//        System.out.println(mapp);

        JSONObject jsonObject = JSON.parseObject("{\n" +
                "\"senceCode\":\"123\",\n" +
                "\"dimLevelDics\":[\n" +
                "{\n" +
                "\"dirShowField\":\"PFTRT_CURVE_NAME\",\n" +
                "\"dirShowName\":\"PFTRT_CURVE_NAME\"\n" +
                "},\n" +
                "{\n" +
                "\"dirShowField\":\"PFTRT_CURVE_NAME\",\n" +
                "\"dirShowName\":\"PFTRT_CURVE_NAME\",\n" +
                "\"parentField\":\"PFTRT_CURVE_NAME\"\n" +
                "}\n" +
                "]\n" +
                "}");
        JSONArray jsonArray = jsonObject.getJSONArray("dimLevelDics");

        System.out.println("");

    }

    public String testAppendString(String s) {
        return s + s;
    }

    public Integer testDoubleInteger(Integer s) {
        return s + s;
    }

    public static boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(dateStr); // 尝试解析日期字符串
            return true; // 如果能解析，则日期合法
        } catch (ParseException e) {
            return false; // 如果解析失败，则日期不合法
        }
    }

}
