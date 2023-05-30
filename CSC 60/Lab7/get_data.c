/*---------------------------------------------------------------*/
/* Ahmed Taeha                                                  */
/* LAB 7   1-dimensional arrays                                  */

#include "lab7.h"

/*-----------------------------------------------------------------*/
/* This function will open the input file, read the data into      */
/* array k, and return real_filesize.                              */
int get_data(const char *filename, /* input, current file name */
             int number_list[])    /* output, the filled array */
{
    FILE *input_file;
    int real_filesize = 0;

    input_file = fopen(filename, "r");
    if (input_file == NULL)
    {
        printf("Error on file-open of file %s \n", filename);
        exit(EXIT_FAILURE);
    }

    while ((fscanf(input_file, "%i", &number_list[real_filesize])) == 1)
    {
        real_filesize += 1;
    }
    return real_filesize;
}
/*-----------------------------------------------------------------*/
