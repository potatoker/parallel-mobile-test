package com.ray.offloading1.Transfer;

import java.io.File;
import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Ray on 2015/4/17.
 */
public class partComparator implements Comparator<File> {

    public int compare(File f1,File f2){

        if(f1.length()>f2.length())
            return 1;
        else
            return 0;

    }

}
