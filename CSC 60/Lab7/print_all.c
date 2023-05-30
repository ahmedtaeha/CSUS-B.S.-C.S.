/*-----------------------------------------------------------------*/
// Ahmed Taeha
/* This function will print all but your name (which is above)     */

#include "lab7.h"

void print_all(const char *filename, /* input, the current filename    */
               int real_filesize,    /* input, actual size of the data */
               int *range_count)     /* input, # of values in range    */
{
    printf("\nYour Name.  Lab 7. \n");
    printf("\nFile %s:  ", filename);
    printf("\nThere are %i values in the range of 90 through 99\n", *range_count);
    printf("out of a total of %i values.", real_filesize);
    printf("\n\n");
    return;
}
/*-----------------------------------------------------------------*/