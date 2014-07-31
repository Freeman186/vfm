/*
 * Sample file chunker written by Sam Birch to
 * demonstrate targeted reading and writing to/from
 * files using standard C calls.
 */
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv)
{
	char filename[21];
	int filekbytes;
	int filesize;
	char chunkchoice;
	int chunksize;
	int chunkcount;
	int modchunk;
	int written;
	int readcount;
	int i;
	char *chunk; // demo using only a single chunk
	FILE *fp;
	
	printf("This program will use C to read in specific chunks of a\n");
	printf("file in and write specific chucks back to the file.\n");
	
	printf("->Type in the name of a new file to create: ");
	fgets(filename,20,stdin);
	strtok(filename,"\n"); //only getting rid of the new line character here.
	
	// Check to make sure the file is new...
	fp = fopen(filename,"r");
	if (fp) {
		printf("That file existed...shutting down.\n");
		fclose(fp);
		exit(-1);
	}
	
	// Create & Open the new file in binary read/write mode
	fp = fopen(filename,"wb+");
	if (fp == NULL) {
		printf("Couldn't create the file...shutting down.\n");
		exit(-2);
	}
	
	// Let's ask how big to make the file:
	do { // How big?
		printf("How many KBytes should we initially fill the file with (1-10): ");
		scanf("%d",&filekbytes);
	} while ((filekbytes < 1) || (filekbytes > 10));
	filesize = filekbytes*1024;
	
	// How large should our chunks be?
	do { // How big are the chunks?
		printf("Select a chunk size: \n ");
		printf("  a) 128 Bytes\n");
		printf("  b) 256 Bytes\n");
		printf("  c) 512 Bytes\n");
		printf("Choose (a,b,c): ");
		chunkchoice = fgetc(stdin);
	} while ((chunkchoice != 'a') && (chunkchoice != 'b') && (chunkchoice != 'c'));

	// We can do the next line ONLY because this is a single char...
	// We couldn't do it if it was a string literal 
	switch (chunkchoice) {
		case 'a':
			chunksize = 128;
			break;
		case 'b':
			chunksize = 256;
			break;
		case 'c':
			chunksize = 512;
			break;
		default:
			printf("Switch error...shouldn't get here...closing.\n");
			exit(-3);
	}
	chunkcount = filesize/chunksize;
	
	printf("> Creating %s and filling with %d \'d\' characters\n",filename,filesize);
	printf(">   - The file is accessible in %d Byte chunks\n",chunksize);
	printf(">   - It contains %d chunk(s) of data\n\n",chunkcount);
	
	// Allocate memory to our single chunk
	chunk = (char *) malloc(chunksize);
	
	// Fill our chunk with the initialization character
	for (i = 0; i < chunksize; i++){
		chunk[i]='d';
	}
	
	// Use this initialized chunk to initialize the file
	rewind(fp); // Not really necessary since this is still an empty file here...
	for (i = 0; i < chunkcount;i++) {
		written = fwrite(chunk,sizeof(char),chunksize,fp);
		if (written != chunksize){
			printf("Error writing chunks during initialization...\n");
			fclose(fp);
			free(chunk);
			exit(-4);
		}
	}
	
	printf("Successfully initialized file!\n");
	do { // What chunk do you want to modify?
		printf("What chunk do you want to modify (0-%d): ",chunkcount-1);
		scanf("%d",&modchunk);
	} while ((modchunk < 0) || (modchunk >= chunkcount));
	printf("-> Now incrementing each character of chunk %d...\n",modchunk);
	
	// First get the chunk from the file...
	fseek(fp,modchunk*chunksize,SEEK_SET); // set the starting position...
	readcount = fread(chunk,sizeof(char),chunksize,fp);
	if (readcount != chunksize){
		printf("Reading error...\n");
			fclose(fp);
			free(chunk);
			exit(-5);		
	}
	
	// Increment each character...
	for (i = 0; i < chunksize; i++){
		chunk[i]++;
	}
	
	// Write that chunk back to the file...
	fseek(fp,modchunk*chunksize,SEEK_SET); // set the starting position...
	written = fwrite(chunk,sizeof(char),chunksize,fp);
	if (written != chunksize){
		printf("Writing error...\n");
			fclose(fp);
			free(chunk);
			exit(-6);		
	}
	printf("Successfully edited chunk %d and wrote the data back to the file.\n",modchunk);
	printf("Open %s in a text editor to see the results...bye\n",filename);
	
	// Close the file
	fclose(fp);
	
	// FREE the memory...FREE the memory!
	free(chunk);
	return 0;
}
