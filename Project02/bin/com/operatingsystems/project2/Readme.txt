Hi, sorry it took a little longer to get you these files. 
I will list the changes Ive made in order for this to work:

	I created a file called Main.java. This contains your code copied and pasted from your main.cc file. 
	I removed the comments and unecessary include statements and also changed the c* variable of type char to a String. 
	I removed the debug() method, or function... 
	I created an object of type Vfm called vfmObject. This is what is being used to access the vfm_c.cc functions. I will describe the changes made to that later.
	Next, I replaced all the function calls (ex. vfmStatus) with the vfmObject.functioncall (ex. vfmObject.vfmStatus). Im just using the object to interact with the other class.
	I replaced all the printf statements with System.out.printf();...I didnt change the text
	Lastly i deleted all the lines that said "delete mystring" . I dont think thats necessary in java, though i might be wrong. 

For the file called vfm_c.cc i replaced it with a java file valed Vfm.java. I once again copied and pasted the code from your c++ file to my java file. I will describe the changes made below.

	removed comments and unecessary includes
	replaced all char *filename with String fileName
	replaced all error() messages to System.err.printf();



That should be about it. Hope this is ok. Everything should compile and run fine. I am using the newest version of eclipse. 

Chris





