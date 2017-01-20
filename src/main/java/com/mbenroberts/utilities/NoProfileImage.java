package com.mbenroberts.utilities;

import java.io.File;

public class NoProfileImage {

    private static File folder = new File("/Users/M.Ben_Roberts/tmp/uploads/profile");
    private static File[] listOfFiles = folder.listFiles();

    public static String getRandomPlaceholder(){

        int randomIndex = (int) (Math.random() * listOfFiles.length);

        return listOfFiles[randomIndex].getName();
    }
}
