/*
 * This program was written by Sam Birch to demonstrate reading 
 * and writing string literals that may cross multiple chunk 
 * boundaries.  The chunks are simple buffers that are similar to
 * pages...but without the over-arching book-keeping or linkage
 * to a physical backing store (file on disk).
 */
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv)
{
	int chunkcount;
	int chunksize;
	int totalsize;
	char **chunks;
	int *chunktable;
	int i,j;
	char message[257];
	char tempmsg[257];
	int targetStart;
	int targetLen;
	int targetEnd;
	
	printf("Welcome to multi-chunker.\n");
	printf("Please provide some setup information...\n");
	do {
		printf("  -> Enter a long sentence: ");
		fgets(message,128,stdin);
		strtok(message,"\n");
		totalsize = strlen(message);
	} while (totalsize < 1);
	do {
		printf("  -> What chunk size do you want to use (1-%d): ",totalsize/2);
		scanf("%d",&chunksize);
	} while ( (chunksize < 1)||(chunksize > totalsize/2));
	
	chunkcount = totalsize / chunksize;
	if (totalsize % chunksize) {
		chunkcount++;
		printf("...extending your sentence %d characters to fill buffer\n",totalsize % chunksize);
		totalsize = chunksize * chunkcount;
		printf(" Total size now %d\n",totalsize);
	}
	
	// Create the chunks...
	// First allocate space for the outer pointer/array
	chunks = (char **) malloc(chunkcount * sizeof *chunks);
	// Now we allocate space for each chunk in the array
	for (i = 0; i < chunkcount; i++) {
		chunks[i] = (char *)malloc(chunksize * sizeof(char));
	}
	
	//Create the chunktable
	chunktable = (int*) malloc (sizeof(int)*chunkcount);
	
	
	/*
	 * In between the malloc and free is where the real work is getting
	 * done in this demo...
	 */
	 
	 printf("Building the chunk table to demonstrate indirection...\n");
	 printf("The chunk table looks like this: \n");
	 printf("VAddress  |  RAddress\n");
	 for (j = 0, i = chunkcount -1; j < chunkcount; j++, i--){
		 printf("   [%d]           %d\n",j,i);
		 chunktable[j]=i;
	 }
	 
	 printf("\n");
	 printf("Let's fill the chunks with your sentence and then print them out by RAddress:\n");
	 // Fill the chunks
	 for (i = 0; i < totalsize; i++) {
		 int vBase;
		 int vOffset;
		 vBase = i / chunksize;
		 vOffset = i % chunksize;
		 // The indirection of the chunktable should put it the chunks into the right location...
		 if (i < strlen(message)) {
			 chunks[chunktable[vBase]][vOffset]= message[i];
		 } else {
			 chunks[chunktable[vBase]][vOffset]='.';
		 }
	 }
	 // Print out the chunks by RAddress:
	 printf("\n");
	 for (i = 0; i < chunkcount; i++) {
		 printf("[%d]: ",i);
		 for (j = 0; j < chunksize; j++) {
			 printf("'%c' ",chunks[i][j]);
		 }
		 printf("\n");
	 }
	 
	 // Do something more interesting
	 printf("\nWe'll print out a range of characters you want: \n");
	 do {
		 printf("  -> Enter the start VAddr (0-%d): ", totalsize);
		 scanf("%d",&targetStart);	 
	 } while ((targetStart < 0) || (targetStart > totalsize));
	 do {
		 printf("  -> How many characters do you want from VAddr[%d] (1-%d): ", targetStart, totalsize-targetStart);
		 scanf("%d",&targetLen);	 
	 } while ((targetLen < 1) || (targetLen > totalsize-targetStart));
	 targetEnd = targetStart+targetLen;
	 
	 // Let's get the block of text asked for
	 strcpy(tempmsg,""); // Initialize
	 for (i=0, j=targetStart-1;j<targetEnd;j++,i++) {
		 int vBase;
		 int vOffset;
		 vBase = j / chunksize;
		 vOffset = j % chunksize;
		 // The indirection of the chunktable should get data in correct VAddr order...
		 tempmsg[i] = chunks[chunktable[vBase]][vOffset];
	 }
	 tempmsg[i]='\0'; // null terminate
	 printf("Your substring is: \"%s\"\n",tempmsg);
	 	
	
	//Free the chunktable
	free(chunktable);
	
	// Need to free the chunks in reverse order: inner first, then outer.
	for (i = 0; i < chunkcount; i++) {
		free(chunks[i]);
	}
	free(chunks);
	printf("Done...\n");
	return 0;
}
