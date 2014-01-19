package org.cc.quartz;

import java.util.Date;

public class MyJob {
	public void work() {  
        System.out.println("date:" + new Date().toString());  
    }  
}
