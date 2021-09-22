package com.ly.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

public class JsonInterfaceTest {
    public interface AInterface{
        String getName();
        void setName(String s);
    }
    public static class AClass implements AInterface{
        private String name;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String s) {
            name=s;
        }
    }
    public static class BClass{
        private String type;

        private AInterface aInterface=new AClass();

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public AInterface getAInterface() {
            return aInterface;
        }

        public void setAInterface(AInterface aInterface) {
            this.aInterface = aInterface;
        }
    }
    public static void main(String[] args) {
        BClass b=new BClass();
        AClass a=new AClass();
        a.setName("asdf");
        b.setAInterface(a);
        b.setType("qer");
        String aa=JSON.toJSONString(b);
        System.out.println(aa);
        //String jsonStr="{aInterface:{name:'asdf'},type:'qer'}";//"{type:'12',aInterface:{name:'wer'}}";
        String jsonStr="{type:'12',aInterface:{name:'wer'}}";
        BClass obj= JSON.parseObject(jsonStr,BClass.class);
        AInterface aInterface=obj.getAInterface();
        System.out.println(aInterface.getName());
        System.out.println(aInterface.getClass());

    }
}
