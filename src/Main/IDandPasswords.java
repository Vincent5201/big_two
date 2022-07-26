package Main;

import java.util.HashMap;

public class IDandPasswords {

    static HashMap<String,String> logininfo=new HashMap<String,String>();

    IDandPasswords(){
    	//ID and Passwords
        logininfo.put("F74101165","f74101165"); 
        logininfo.put("F74105313","f74105313");
        logininfo.put("F74106165","f74106165");
        logininfo.put("0","0");//for test !
    }

    public HashMap getLoginInfo(){
        return logininfo;
    }
}

