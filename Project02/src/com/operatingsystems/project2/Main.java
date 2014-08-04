package com.operatingsystems.project2;

public class Main {
	
	public static void main(String[] args){
		
	
			//vars
			int	i = 0;
			int  testcount = 5;
			int	testsize  = 512;
			String mystring;

			Vfm vfmObject = new Vfm();	//new object of vfm is being created, used to call the methods within the Vfm.c
			
			vfmObject.vfmstatus();
			vfmObject.vfminit(testcount,testsize);
			vfmObject.vfmstatus();
			System.out.printf("MAIN: Creating Files 1-5\n");
			
			vfmObject.vfmcreate("test1",1000);
			vfmObject.vfmcreate("test2",2000);
			vfmObject.vfmcreate("test3",3000);
			vfmObject.vfmcreate("test4",4000);
			vfmObject.vfmcreate("test5",5000);
			
			System.out.printf("MAIN: Reading TEST5\n");
			mystring = vfmObject.vfmread("test5",2000,100,i);
			System.out.printf("MAIN: test5 result =\"%s\"\n",mystring);
			
			System.out.printf("MAIN: Reading TEST1\n");
			mystring = vfmObject.vfmread("test1",1001,23,i);
			System.out.printf("MAIN: test1 result =\"%s\"\n",mystring);
			
			vfmObject.vfmstatus();
			
			System.out.printf("MAIN: Pre-Reading TEST4\n");
			mystring = vfmObject.vfmread("test4",3000,30,i);
			System.out.printf("MAIN: test4 result =\"%s\"\n",mystring);
			
			vfmObject.vfmstatus();

			mystring = "YabaDabaDo";
			
			System.out.printf("MAIN: Writing TEST4\n");
			vfmObject.vfmwrite("test4",3000,mystring);
			vfmObject.vfmstatus();

			System.out.printf("MAIN: Verify Read of TEST4\n");
			mystring = vfmObject.vfmread("test4",2999,30,i);
			System.out.printf("MAIN: test4 result =\"%s\"\n",mystring);

			System.out.printf("MAIN: Verify Read of TEST5\n");
			mystring = vfmObject.vfmread("test5",2999,30,i);
			System.out.printf("MAIN: test5 result =\"%s\"\n",mystring);

			System.out.printf("MAIN: Verify Read of TEST4\n");
			mystring = vfmObject.vfmread("test4",2999,30,i);
			System.out.printf("MAIN: test4 result =\"%s\"\n",mystring);
			vfmObject.vfmstatus();
			
			vfmObject.vfmclose("test1");
			vfmObject.vfmclose("test2");
			vfmObject.vfmclose("test3");
			vfmObject.vfmclose("test4");
			vfmObject.vfmclose("test5");

			System.out.printf("bye...\n");
		
	}

}
