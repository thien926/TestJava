package com.java.dictionarys;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;


public class DictionaryExample {
	
	public static void hashTableMethod() {
		Hashtable<String, String> dict = new Hashtable<>(); 
		
		/* create or update */
        dict.put("Hello", "Xin chao");  
        dict.put("Goodbye", "Tam biet");  
        dict.put("Thank you", "Cam on");
        dict.put("Thank you", "Cam on ban");
        System.out.println("dict is empty?: " + dict.isEmpty());
        System.out.println("size dict: " + dict.size());
        System.out.println("dict: " + dict);
        
        /* remove */
        dict.remove("Hello");
        System.out.println("dict: " + dict);
        
		/* get */
        System.out.println("get Thank you: " + dict.get("Thank you"));
        System.out.println("get Hello: " + dict.get("Hello"));
        Set<String> keySet = dict.keySet();
        for(String item : keySet) {
        	System.out.println(item + " : " + dict.get(item));
        }
	}
	
	public static void hashMapMethod() {
		HashMap<String, String> dict = new HashMap<String, String>();
		
		/* hashmap cho phép 1 key là null, cũng cho phép nhiều value = null */
		/* create or update */
		dict.put(null, null);
		dict.put(null, "null1");
		dict.put("null", null);
		dict.put("null1", null);
	    dict.put("England", "London");
	    dict.put("Germany", "Berlin");
	    dict.put("Norway", "Oslo");
	    dict.put("USA", "Test");
	    dict.put("USA", "Washington DC");
	    System.out.println("dict: " + dict);
	    
	    Set<String> keySet = dict.keySet();
        for(String item : keySet) {
        	System.out.println(item + " : " + dict.get(item));
        }
	}
	
	public static void main(String args[]){ 
//		hashTableMethod();
		hashMapMethod();
    }  
}
