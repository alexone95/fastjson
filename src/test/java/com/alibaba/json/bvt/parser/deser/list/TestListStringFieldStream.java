package com.alibaba.json.bvt.parser.deser.list;

import java.io.StringReader;
import java.util.*;

import com.alibaba.fastjson.JSONException;
import org.junit.Assert;

import com.alibaba.fastjson.JSONReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestListStringFieldStream {

    private Object result;

    private JSONReader reader;

    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters() {

        return Arrays.asList(new Object[][]{
                // Suite di Test
                { generateJSONReader("{\"values\":[\"a\",null,\"b\",\"ab\\\\c\\\"\"]}"),
                        Arrays.asList("a", null, "b", "ab\\c\"") },
                //{ generateJSONReader("{\"values\":null}"), null},
                { generateJSONReader("{\"values\":[]}"), Arrays.asList()},
                { generateJSONReader("{\"values\":[\"b\"["), new JSONException()},
                { generateJSONReader("{\"values\":[n"), new JSONException()},
                { generateJSONReader("{\"values\":[\"b\"["), new JSONException()}

        });
    }

    public TestListStringFieldStream(JSONReader reader, Object result) {
        this.reader = reader;
        this.result = result;
    }

    public static JSONReader generateJSONReader(String text){
        return new JSONReader(new StringReader(text));
    }

    @Test
    public void testStringFieldTest(){
        try{
            if (result.getClass() == JSONException.class)
                reader.startObject();
            Model model = reader.readObject(Model.class);
            List<String> values = model.getValues();
            List<Object> resultList = (List<Object>)result;
            if (values != null){
                Assert.assertEquals(values.size(), resultList.size());
                for (int i=0; i < values.size(); i++) {
                    Assert.assertEquals(values.get(i), resultList.get(i));
                }
            }
            else{
                Assert.assertNull(values);
            }

        }
        catch (Exception e){
            Assert.assertEquals(e.getClass() , result.getClass());
        }
    }


    public static class Model {

        private List<String> values;

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

    }
}
