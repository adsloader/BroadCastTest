package com.campandroid.My;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.campandroid.broadcasttest.ListData;

import android.content.Context;
import android.content.SharedPreferences;

public class MyDataManager {
	
	public static final String STATE_PREFER       = "STATE_PREFER";
	public static final String PREFER             = "PREFER";
	
	// 환경설정을 저장한다(실행정보).
	static public void SaveState(Context ctx, boolean b){
		SharedPreferences prefs = ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(STATE_PREFER, b);
		editor.commit();		
	}
	
	// 환경설정을 가져온다(실행정보).
	static public boolean GetState(Context ctx){
		SharedPreferences prefs = ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE);
		 return prefs.getBoolean(STATE_PREFER, true);
	}
	
	// 카운트정보를 저장한다.
    static public ArrayList<ListData> LoadData(Context context) throws Exception{
		
    	FileInputStream fis = context.openFileInput("object.ser");
	    ObjectInputStream objstream2 = new ObjectInputStream(fis);
	    Object object = objstream2.readObject();
	    objstream2.close();
	    
	    return (ArrayList<ListData>)object;
	}
    
    static public void SaveData(Context context, Object Items) throws Exception{
		FileOutputStream fos = context.openFileOutput("object.ser", Context.MODE_PRIVATE);
	    ObjectOutputStream objstream = new ObjectOutputStream(fos);
	    objstream.writeObject(Items);
	    objstream.close();	
	}
        
    static public void AddData(Context context, ListData d){
    	ArrayList<ListData> Items = null;
    	try{
    		Items = MyDataManager.LoadData(context);
		} catch(Exception e){
		    Items = new ArrayList<ListData>();	
		}
    	
    	try{
    		Items.add(0, d);
    		MyDataManager.SaveData(context, Items);
    		
    	} catch(Exception e){
    		
    	}
	}
    
    
	
}
