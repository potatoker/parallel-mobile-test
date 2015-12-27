package com.ray.offloading1.IPContacts;

/**
 * Created by Ray on 2015/4/8.
 */
public class serverHelper {
  int _id;
   String name;
    String IP;
    int level;

    public serverHelper(){}

    public serverHelper(int _id,String _name,String _IP,int _level){
        this._id=_id;
        name=_name;
        IP=_IP;
        level=_level;
    }
    public serverHelper(String _name,String _IP,int _level){
        this._id=0;
        name=_name;
        IP=_IP;
        level=_level;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
