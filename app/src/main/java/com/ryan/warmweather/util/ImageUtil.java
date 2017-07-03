package com.ryan.warmweather.util;

import android.content.res.Resources;

import com.ryan.warmweather.MyApplication;
import com.ryan.warmweather.R;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Think on 2017/7/2.
 */

public class ImageUtil {


    public static int getImageId(String str) {
        String imageName;
        if(str.startsWith("0")){
            str = str.substring(1);
        }
        imageName = "a_"+str;
        return MyApplication.getContext().getResources().getIdentifier
                (imageName,"drawable","com.ryan.warmweather");

    }
}
