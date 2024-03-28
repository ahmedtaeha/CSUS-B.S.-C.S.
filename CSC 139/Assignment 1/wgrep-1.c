#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void grepStdin(char *searchTerm)
{
    char *line = NULL;
    size_t len = 0;
    while (getline(&line, &len, stdin) != -1)
    {
        if (strstr(line, searchTerm))
        {
            printf("%s", line);
        }
    }
    free(line);
}

void grepFile(FILE *fp, char *searchTerm)
{
    char *line = NULL;
    size_t len = 0;
    while (getline(&line, &len, fp) != -1)
    {
        if (strstr(line, searchTerm))
        {
            printf("%s", line);
        }
    }
    free(line);
}

int main(int argc, char *argv[])
{
    if (argc < 2)
    {
        printf("wgrep: searchterm [file ...]\n");
        exit(1);
    }

    if (argc == 2)
    {
        grepStdin(argv[1]);
    }
    else
    {
        for (int i = 2; i < argc; i++)
        {
            FILE *fp = fopen(argv[i], "r");
            if (fp == NULL)
            {
                printf("wgrep: cannot open file\n");
                exit(1);
            }
            grepFile(fp, argv[1]);
            fclose(fp);
        }
    }
    return 0;
}
