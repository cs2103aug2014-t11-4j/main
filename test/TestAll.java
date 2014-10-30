package test;

import indigoSrc.LogicFacade;

import java.util.Scanner;
public class TestAll {
	
	public static Scanner sc = new Scanner(System.in);
	public static void main(String args[]){
		System.out.println("Hello, we now R");
		String inp = sc.nextLine();
		
		while(!inp.equals("pickety")){
			LogicFacade id = new LogicFacade(inp);
			System.out.println(id.feedback);
			System.out.println(id.display);
			inp = sc.nextLine();
		}
	}
}
