package com.serenegiant.myhome.Util;

import android.os.Environment;

import java.io.File;

public class Urls {
    public static String jsonUrl = Environment.getExternalStorageDirectory()+ File.separator+"MyHome"+File.separator+"jsons"+File.separator;
    public static String dishesUrl = Environment.getExternalStorageDirectory()+ File.separator+"MyHome"+File.separator+"dished"+File.separator;
}
