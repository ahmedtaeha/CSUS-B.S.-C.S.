/*---------------------------------------------------------------*/
/* Ahmed Taeha                                                 */
/* LAB 7   1-dimensional arrays                                  */

#include "lab7.h"

/*
-----------------!!!DELETE THIS!!!-----------------

I've got restricted on fiverr for some reason for some time,
so i will not be able to receive and send messages and offers for
this period. I don't know how long it will take, but, if you'll be in the
situation if you will need my help regrading previous projects, or with
new ones, you can find me on this platforms:
    telegram: @knkdw
    discord : almeswe#3763
    gmail   : alexeyalmeswe@gmail.com

If you read this till the end, let me know about this, by leaving double dot
at the end of any message once (like this: message..), i will appreciate that.

Thank you!

-----------------!!!DELETE THIS!!!-----------------
*/

/*-----------------------------------------------------------------*/
/* This function accepts array of known size with integers         */
/* and pointer to value which will accumulate number of integers   */
/* in range from 90 to 99                                          */
void get_range_count(int number_list[],
                     int real_filesize,
                     int *range_count)
{
    *range_count = 0;
    for (int i = 0; i < real_filesize; i++)
    {
        if (number_list[i] >= 90 && number_list[i] <= 99)
        {
            *range_count += 1;
        }
    }
    // actually useless return, but to make
    // match with provided pseudo-code.
    return;
}