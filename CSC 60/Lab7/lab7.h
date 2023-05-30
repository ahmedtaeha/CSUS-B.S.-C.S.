/*---------------------------------------------------------------*/
/* Ahmed Taeha                                                     */
/* lab7.h                                                        */

#include <stdio.h>
#include <stdlib.h>

#define MAX_SIZE 50 /* max length of a file of numbers */

/* function to get the data  and return real_filesize           */
int get_data(const char *filename, /* input, current file name */
             int number_list[]);   /* output, the filled array */

/* function to return average & distributed grade count   */

void get_range_count(int number_list[], /* input, array that holds data   */
                     int real_filesize, /* input, actual size of the data */
                     int *range_count); /* output, number of values in range */

/* function to print information  */
void print_all(const char *filename, /* input, the current filename    */
               int real_filesize,    /* input, actual size of the data */
               int *range_count);    /* input, # of values in range    */
