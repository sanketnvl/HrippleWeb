package com.hripple.controller;

import java.util.Arrays;



public class Test {
	
	public static int getSecondLargest(int[] a, int total){  
				Arrays.sort(a);
		
		       return a[total-2];  
	}  
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[]={1,2,5,6,3,2};  
		System.out.println("Second Largest: "+getSecondLargest(a,6));  
	}

}
