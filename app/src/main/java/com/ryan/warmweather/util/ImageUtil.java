package com.ryan.warmweather.util;

import com.ryan.warmweather.MyApplication;


/**
 * Created by Think on 2017/7/2.
 */

public class ImageUtil {


    public static int getImageId(String str) {
        String imageName;
        if(str.startsWith("0")){
            str = str.substring(1);
        }
        if(str.equals("53")){
            str = "18";
        }
        imageName = "a_"+str;
        return MyApplication.getContext().getResources().getIdentifier
                (imageName,"drawable","com.ryan.warmweather");

    }
}
