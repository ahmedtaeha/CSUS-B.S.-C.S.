/* This example is stolen from Dr. Raju Rangaswami's original 4338
   demo and modified to fit into our lecture. */

#include <stdio.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

#define MAX_ARGS 20
#define BUFSIZE 1024

char *path[10];

int get_args(char *cmdline, char *args[])
{
  int i = 0;

  /* if no args */
  if ((args[0] = strtok(cmdline, "\n\t ")) == NULL)
    return 0;

  while ((args[++i] = strtok(NULL, "\n\t ")) != NULL)
  {
    if (i >= MAX_ARGS)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
  }
  /* the last one is always NULL */
  return i;
}

int get_args_pipe(char *cmdline, char *args[])
{
  int i = 0;

  /* if no args */
  if ((args[0] = strtok(cmdline, "|")) == NULL)
  {
    return 0;
  }

  while ((args[++i] = strtok(NULL, "|")) != NULL)
  {
    if (i >= MAX_ARGS)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
  }
  /* the last one is always NULL */
  return i;
}

// returns index if has '>' symbol, -1 otherwise
int has_output(char *args[], int nargs)
{
  int i = 0;
  for (i = 0; i < nargs; i++)
  {
    if (strcmp(args[i], ">") == 0)
      return i;
  }
  return -1;
}

// returns index if has '>>' symbol, -1 otherwise
int has_output_append(char *args[], int nargs)
{
  int i = 0;
  for (i = 0; i < nargs; i++)
  {
    if (strcmp(args[i], ">>") == 0)
      return i;
  }
  return -1;
}

// returns index if has '<' symbol, -1 otherwise
int has_input(char *args[], int nargs)
{
  int i = 0;
  for (i = 0; i < nargs; i++)
  {
    if (strcmp(args[i], "<") == 0)
      return i;
  }
  return -1;
}

// returns 1 if args have '<' and '>' or symbols, 0 otherwise
int has_input_output(char *args[], int nargs)
{
  if (has_output(args, nargs) != -1 && has_input(args, nargs) != -1)
  {
    return 1;
  }
  else
  {
    return 0;
  }
}

void execute_simple(char *args[], int nargs)
{
  int pid, async;
  /* check if async call */
  if (!strcmp(args[nargs - 1], "&"))
  {
    async = 1;
    args[--nargs] = 0;
  }
  else
    async = 0;

  pid = fork();
  if (pid == 0)
  { /* child process */
    execvp(args[0], args);
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(-1);
  }
  else if (pid > 0)
  { /* parent process */
    if (!async)
      waitpid(pid, NULL, 0);
  }
  else
  { /* error occurred */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(1);
  }
}

void process_output(char *args[], int nargs, char *filename)
{
  int pid, async;
  /* check if async call */
  if (!strcmp(args[nargs - 1], "&"))
  {
    async = 1;
    args[--nargs] = 0;
  }
  else
    async = 0;

  // open file to write output
  int fd1 = open(filename, O_WRONLY | O_CREAT | O_TRUNC, 0644);

  pid = fork();
  if (pid == 0)
  { /* child process */
    if (dup2(fd1, 1) != 1)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
    close(fd1);

    execvp(args[0], args);
    /* return only when exec fails */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(-1);
  }
  else if (pid > 0)
  { /* parent process */
    if (!async)
      waitpid(pid, NULL, 0);
  }
  else
  { /* error occurred */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(1);
  }
}

void process_output_append(char *args[], int nargs, char *filename)
{
  int pid, async;
  /* check if async call */
  if (!strcmp(args[nargs - 1], "&"))
  {
    async = 1;
    args[--nargs] = 0;
  }
  else
    async = 0;

  // open file to write output
  int fd1 = open(filename, O_WRONLY | O_CREAT | O_APPEND, 0644);

  pid = fork();
  if (pid == 0)
  { /* child process */
    if (dup2(fd1, 1) != 1)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
    close(fd1);

    execvp(args[0], args);
    /* return only when exec fails */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(-1);
  }
  else if (pid > 0)
  { /* parent process */
    if (!async)
      waitpid(pid, NULL, 0);
  }
  else
  { /* error occurred */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(1);
  }
}

void process_input(char *args[], int nargs, char *filename)
{
  int pid, async;
  /* check if async call */
  if (!strcmp(args[nargs - 1], "&"))
  {
    async = 1;
    args[--nargs] = 0;
  }
  else
    async = 0;

  // open file to read output
  int fd1 = open(filename, O_RDONLY, 0644);

  pid = fork();
  if (pid == 0)
  { /* child process */
    if (dup2(fd1, 0) != 0)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
    close(fd1);

    execvp(args[0], args);
    /* return only when exec fails */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(-1);
  }
  else if (pid > 0)
  { /* parent process */
    if (!async)
      waitpid(pid, NULL, 0);
  }
  else
  { /* error occurred */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(1);
  }
}

void process_input_output(char *args[], int nargs, char *inFilename, char *outFilename)
{
  int pid, async;
  /* check if async call */
  if (!strcmp(args[nargs - 1], "&"))
  {
    async = 1;
    args[--nargs] = 0;
  }
  else
    async = 0;

  // open file to read output
  int fd1 = open(inFilename, O_RDONLY, 0644);
  // open file to write output
  int fd2 = open(outFilename, O_WRONLY | O_CREAT | O_APPEND, 0644);

  pid = fork();
  if (pid == 0)
  { /* child process */
    // replace stdin with input file
    if (dup2(fd1, 0) != 0)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
    close(fd1);

    // replace stdout with output file
    if (dup2(fd2, 1) != 1)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      // exit(1);
    }
    close(fd2);

    execvp(args[0], args);
    /* return only when exec fails */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(-1);
  }
  else if (pid > 0)
  { /* parent process */
    if (!async)
      waitpid(pid, NULL, 0);
  }
  else
  { /* error occurred */
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    // exit(1);
  }
}

void process_pipe(char *cmdline, char **args[], int nargs[], int npipes)
{
  if (npipes == 1)
  {
    int pipes[2];
    int pid;

    pipe(pipes);

    pid = fork();
    if (pid == 0)
    {
      dup2(pipes[0], 0);
      close(pipes[1]);

      execvp(args[0][0], args[0]);
    }
    else
    {
      dup2(pipes[1], 1);
      close(pipes[0]);
      execvp(args[1][0], args[1]);
    }
  }
  // else
  // {
  //   system(cmdline);
  // }
}

void change_dir(char *args[], int nargs)
{
  if (nargs == 2)
  {
    if (chdir(args[1]) == -1)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
    }
  }
  else
  {
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
  }
}

void update_path(char *tokens[])
{
  int i = 0;
  while (1)
  {
    if (tokens[i] == NULL)
    {
      path[i] = NULL;
      break;
    }
    else
    {
      path[i] = (char *)malloc(strlen(tokens[i]) + 1);
      strcpy(path[i], tokens[i]);
    }
    i++;
  }
}

void execute(char *cmdline)
{
  int pid, async;
  char *args[MAX_ARGS];
  char *copy = cmdline;

  int nargs = 0, flag = 0; // flag to check if args have pipe symbol

  if (strstr(cmdline, "|"))
  {
    // system(cmdline);
    nargs = get_args_pipe(cmdline, args);
    flag = 1;
  }

  if (!flag)
  {
    nargs = get_args(cmdline, args);
  }

  if (nargs <= 0)
    return;

  if ((!strcmp(args[0], "quit") || !strcmp(args[0], "exit")) && nargs == 1)
  {
    exit(0);
  }
  if ((strcmp(args[0], "quit") == 0 || strcmp(args[0], "exit") == 0) && nargs > 1)
  {
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    return;
  }

  int flag_output = has_output(args, nargs);
  int flag_input = has_input(args, nargs);
  int flag_output_append = has_output_append(args, nargs);
  int flag_input_output = has_input_output(args, nargs);

  if(path[0] == NULL)
    return;

  if (strcmp(args[0], "cd") == 0)
  {
    change_dir(args, nargs);
  }
  else if (strcmp(args[0], "path") == 0)
  {
    update_path(args + 1);
  }
  else if (flag)
  {
    int i, j;
    // split args of pipe to further args (if possible)
    char **args2[MAX_ARGS];
    int nargs2[MAX_ARGS];

    for (i = 0; i < nargs; i++)
    {
      args2[i] = malloc(sizeof(char *) * BUFSIZE);
      nargs2[i] = get_args(args[i], args2[i]);
    }

    process_pipe(copy, args2, nargs2, nargs);
  }
  // check if args have '<' and '>' symbole
  else if (flag_input_output == 1)
  {
    // get index of input symbol
    int input_index = has_input(args, nargs);

    char inFilename[100];
    char outFilename[100];
    // get input file name
    strcpy(inFilename, args[input_index + 1]);

    // get index of output symbol
    int output_index = has_output(args, nargs);

    // get output file name
    strcpy(outFilename, args[output_index + 1]);

    // put empty string "" on input_index and next to input_index
    strcpy(args[input_index], "");
    strcpy(args[input_index + 1], "");
    // put empty string "" on output_index and next to output_index
    strcpy(args[output_index], "");
    strcpy(args[output_index + 1], "");

    char *args2[MAX_ARGS];
    // copy remianing arguments into new args array
    int i, index = 0;
    for (i = 0; i < nargs; i++)
    {
      if (strcmp(args[i], "") != 0)
      {
        args2[index] = malloc(sizeof(char *) * BUFSIZE);
        strcpy(args2[index++], args[i]);
      }
    }
    args2[index] = malloc(sizeof(char *) * BUFSIZE);
    args2[index] = NULL;
    process_input_output(args2, index, inFilename, outFilename);
  }
  // check if args has output to file symbol >
  else if (flag_output != -1)
  {
    // get file name after > symbol
    char *filename = args[flag_output + 1];
    // put null char at location of >
    args[flag_output] = NULL;

    process_output(args, nargs, filename);
  }
  else if (flag_output_append != -1)
  {
    // get file name after >> symbol
    char *filename = args[flag_output_append + 1];
    // put null char at location of >>
    args[flag_output_append] = NULL;

    process_output_append(args, nargs, filename);
  }
  else if (flag_input != -1)
  {
    // get file name after < symbol
    char *filename = args[flag_input + 1];
    // put null char at location of <
    args[flag_input] = NULL;

    process_input(args, nargs, filename);
  }
  else
  {
    execute_simple(args, nargs);
  }
}

int main(int argc, char *argv[])
{
  char *cmdline = NULL;
  size_t input_size = 0;
  // char cmdline[BUFSIZE];
  int flag = 0;
  FILE *fp;
  if (argc == 2)
  {
    fp = fopen(argv[1], "r");
    if (fp == NULL)
    {
      char error_message[30] = "An error has occurred\n";
      write(STDERR_FILENO, error_message, strlen(error_message));
      exit(1);
    }
    flag = 1;
  }
  else if (argc > 2)
  {
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(1);
  }

  char cwd[BUFSIZ];
  path[0] = (char*)malloc(sizeof(char) * BUFSIZ);
  getcwd(cwd, sizeof(cwd));

  while (1)
  {
    int res;
    if (flag)
    {
      res = getline(&cmdline, &input_size, fp);
    }
    else
    {
      printf("wish> ");
      res = getline(&cmdline, &input_size, stdin);
    }

    if (res == -1)
    {
      exit(0);
    }

    if (res == 1)
    {
      continue;
    }

    execute(cmdline);
  }
  return 0;
}
