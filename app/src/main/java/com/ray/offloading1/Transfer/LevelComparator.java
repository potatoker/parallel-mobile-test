package com.ray.offloading1.Transfer;

import com.ray.offloading1.IPContacts.serverHelper;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Ray on 2015/4/17.
 */
public class LevelComparator implements Comparator<serverHelper> {

    public int compare(serverHelper o1,serverHelper o2){
        serverHelper sh=(serverHelper)o1;
        serverHelper sh2=(serverHelper)o2;

        if(sh.getLevel()>sh2.getLevel())
            return 1;
        else
            return 0;
    }

}
