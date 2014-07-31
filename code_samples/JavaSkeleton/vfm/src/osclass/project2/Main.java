package osclass.project2;

import osclass.project2.VirtualFileManager;

public class Main {
	
	public static void main(String[] args) {
		int testcount = 5;
		int	testsize  = 512;
		String mystring = null;

		VirtualFileManager vfm = new VirtualFileManager(testcount, testsize);	//new object of vfm is being created, used to call the methods within the Vfm.c
		vfm.vfmstatus();
		System.out.printf("MAIN: Creating Files 1-5\n");
		
		vfm.vfmcreate("test1",1000);
		vfm.vfmcreate("test2",2000);
		vfm.vfmcreate("test3",3000);
		vfm.vfmcreate("test4",4000);
		vfm.vfmcreate("test5",5000);
		
		System.out.printf("MAIN: Reading TEST5\n");
		
	
		// Example to show handling the exception of a failed read...
		try {
			mystring = vfm.vfmread("test5",2000,100);
		}
		catch (Exception exception) {
			System.err.println("Couldn't read file");
		}
		System.out.printf("MAIN: test5 result =\"%s\"\n",mystring);
		System.out.printf("MAIN: Reading TEST1\n");
		try {
			mystring = vfm.vfmread("test1",1001,23);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("MAIN: test1 result =\"%s\"\n",mystring);
		vfm.vfmstatus();

		System.out.printf("MAIN: Pre-Reading TEST4\n");
		try {
			mystring = vfm.vfmread("test4",3000,30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("MAIN: test5 result =\"%s\"\n",mystring);
		vfm.vfmstatus();

		mystring = "YabaDabaDo";
		
		System.out.printf("MAIN: Writing TEST4\n");
		vfm.vfmwrite("test4",3000,mystring);
		vfm.vfmstatus();

		System.out.printf("MAIN: Verify Read of TEST4\n");
		try {
			mystring = vfm.vfmread("test4",2999,30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("MAIN: test4 result =\"%s\"\n",mystring);

		System.out.printf("MAIN: Verify Read of TEST5\n");
		try {
			mystring = vfm.vfmread("test5",2999,30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("MAIN: test5 result =\"%s\"\n",mystring);

		System.out.printf("MAIN: Verify Read of TEST4\n");
		try {
			mystring = vfm.vfmread("test4",2999,30);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("MAIN: test4 result =\"%s\"\n",mystring);
		vfm.vfmstatus();

		System.out.printf("bye...\n");
	
	}

}
