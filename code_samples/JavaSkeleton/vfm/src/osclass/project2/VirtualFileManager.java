/**
 * 
 */
package osclass.project2;

/**
 * @author Sam
 *
 */
public class VirtualFileManager {
	// Fields...
	int framecount;
	int framesize;
	
	/**
	 * Initializes the VirtualFileManager instance.  This is the 
	 * replacement for vfminit() in the C version of this project.
	 * 
	 * @param framecount
	 * @param framesize
	 */
	public VirtualFileManager(int framecount, int framesize) {
		super();
		this.framecount = framecount;
		this.framesize = framesize;
		
	}
	
	/**
	 * Alternate constructor that initializes the framecount by 
	 * parameter but uses the promised default of 512 Bytes for 
	 * the frame size.
	 * 
	 * @param framecount
	 */
	public VirtualFileManager(int framecount) {
		super();
		this.framecount = framecount;
		this.framesize = 512; // Default for this project
	}

	/**
	 * Creates a file in the VirtualFileManager instance.
	 * 
	 * @param filename
	 * @param filesize
	 * @return 0 for success...any other number is an error
	 */
	public int vfmcreate(String filename, int filesize){
		System.err.printf("vfmcreate() hasn't been implemented yet\n");
		return 0;
		
	}

	/**
	 * Opens a file in the VirtualFileManager instance.
	 * 
	 * @param filename
	 * @return 0 for success...any other number is an error
	 */
	public int vfmopen(String filename){
		System.err.printf("vfmopen() hasn't been implemented yet\n");
		return 0;
		
	}
	
	/**
	 * Closes a file open in the VirtualFileManager
	 * 
	 * @param filename
	 * @return 0 for success...any other number is an error
	 */
	public int vfmclose(String filename){
		System.err.printf("vfmclose() hasn't been implemented yet\n");
		return 0;
		
	}

	/**
	 * Reads specific Bytes from filename.  This will be different than the C
	 * version since you can't pass the primitive type int by reference in 
	 * order to get an error code back.  So we'll use exceptions in the case
	 * of an error.
	 * 
	 * @param filename
	 * @param address
	 * @param length
	 * @throws Generic Exception if something goes wrong with the read
	 * @return string read from file
	 */
	public String vfmread(String filename, int address, int length) throws Exception{
		// Just a sample to show the throwing of an exception to replace the 
		// pass-by-reference parameter err that was used in the C version.
		boolean failed = (filename .equals("test5"));
		if (failed) {
			throw new Exception(); //Super vauge "error", but it should work...
		}
		
		System.err.printf("vfmread() hasn't been implemented yet\n");
		return "some misc text";
		
	}

	/**
	 * Writes specific Bytes to filename.  This will be different than the C
	 * version since you can't pass the primitive type int by reference in 
	 * order to get an error code back...TBD? //FIXME
	 * 
	 * @param filename
	 * @param address
	 * @param value
	 * @return 0 for success...any other number is an error
	 */
	public int vfmwrite(String filename, int address, String value){
		System.err.printf("vfmwrite() hasn't been implemented yet\n");
		return 0;
		
	}
	
	/**
	 * Prints a formatted report about the file names of all
	 * open files in the VFM, and the pagetable for each. 
	 */
	public void vfmstatus() {
		System.err.printf("vfmstatus() hasn't been implemented yet\n");
		
	}
	
}
