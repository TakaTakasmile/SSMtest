package com.bjpowernod;

import com.bjpowernode.utils.MD5Util;
import org.junit.Test;

public class MyTest {

    @Test
    public void testMD5(){
        String str = MD5Util.getMD5("000000");
        System.out.println(str);
    }

}
