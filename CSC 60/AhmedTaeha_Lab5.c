/*--------------------------------------------*/
/* Ahmed Taeha                                */
/* Lab 5                                      */
/* Figure the area of a parabola  using files */

#include <stdio.h>
#include <stdlib.h>

#define IN_FILE_NAME "lab5.dat"
#define OUT_FILE_NAME "lab5.txt"

/*
    Prints specified error message
    to `stderr`, along with errno and exits
    with failure code (1).
*/
void panic(const char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

/*
    Entry point for lab5 executable.
*/
int main(void)
{
    double length, depth, area;              // define three variables, needed for calculation process.
    FILE *infile = fopen(IN_FILE_NAME, "r"); // open input file, via `fopen` call with read-only permission.
    if (infile == NULL)
    {                                              // check if input file was opened.
        panic("Cannot open \'" IN_FILE_NAME "\'"); // if not, print error message and exit with error code.
    }
    FILE *outfile = fopen(OUT_FILE_NAME, "w"); // open input file, via `fopen` call with read/write permission.
                                               // also creates the file if needed, or clears all data in it.
    if (outfile == NULL)
    {                                               // check if output file was opened.
        fclose(infile);                             // if not, close opened input file
        panic("Cannot open \'" OUT_FILE_NAME "\'"); // print error message & exit with error code.
    }
    fprintf(outfile, "\nAhmed Taeha.  Lab 5. \n\n");
    fprintf(outfile, "Data on Parabolas \n\n");
    fprintf(outfile, " Length      Depth        Area   \n");
    fprintf(outfile, "--------   ---------   ----------\n");
    while ((fscanf(infile, "%lf %lf\n", &length, &depth)) == 2)
    {                                                                           // loop until we able to read line from input file
        area = (2.0 * length * depth) / 3.0;                                    // calculate parabola's area, according to formula provided in .docx
        fprintf(outfile, "%7.2lf    %7.2lf    %10.3lf\n", length, depth, area); // write result to output file
    }
    fprintf(outfile, "\n\n");
    fclose(infile);      // finally, close input..
    fclose(outfile);     // and output files.
    return EXIT_SUCCESS; // return 0 code (success).
}