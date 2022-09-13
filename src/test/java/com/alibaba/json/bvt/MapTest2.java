package com.alibaba.json.bvt;

import java.util.*;

import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class MapTest2 {

    private final String json;
    private final Object result;

    private final static ParserConfig defaultRedisConfig = new ParserConfig();

    public MapTest2(String json, Object result) {
        this.json = json;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {

        return Arrays.asList(new Object[][]{
                // Suite di Test
                { "{1:\"2\",\"3\":4,'5':6}", generateMap(Arrays.<Object>asList(1, "3", "5"),
                        Arrays.<Object>asList("2", 4, 6)) },
                { null , new NullPointerException() },
                { "{1:\"2\",7:4,'5':6}", generateMap(Arrays.<Object>asList(1, 7, "5"),
                        Arrays.<Object>asList("2", 4, 6)) },

                // Coverage

        });
    }

    public static Map<Object, Object> generateMap(List<Object> keys, List<Object> values ){
        if (keys == null && values == null){
            return null;
        }
        Iterator<Object> keyIter = keys.iterator();
        Iterator<Object> valIter = values.iterator();
        Map<Object, Object> map = new HashMap<Object, Object>();
        while (keyIter.hasNext() || valIter.hasNext())
            map.put(keyIter.next(), valIter.next());
        return map;
    }

    @Test
    public void test_map () {
        try{
            Map<Object, Object> map = JSON.parseObject(json, new TypeReference<Map<Object, Object>>() {});

            for (Object key : map.keySet()) {
                Assert.assertEquals(map.get(key), ((Map<?, ?>) result).get(key));
            }
        }
        catch (Exception e){
            Assert.assertEquals(e.toString() , result.toString());
        }

    }
}
