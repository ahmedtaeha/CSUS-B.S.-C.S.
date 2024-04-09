#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdbool.h>

#define clear() printf("\033[H\033[J")
#define MAX_ARGS 64
#define MAX_PATH_LENGTH 1024
#define MAX_COMMAND_LENGTH 1024
#define MAX_HISTORY_SIZE 100
char error_message[30] = "An error has occurred\n";
char *path[10];

void initialize()
{
  clear();
}

void printDir()
{
  char cwd[1024];
  if (getcwd(cwd, sizeof(cwd)) != NULL)
  {
    printf("\nDir: %s: ", cwd);
  }
  else
  {
    perror("getcwd() error");
  }
}

bool StringCompare(char *a, char b[])
{
  bool equal = false;
  for (int i = 0; a[i] != '\0'; i++)
  {
    if (a[i] == b[i])
    {
      if (a[i + 1] == '\0' || b[i + 1] == '\0')
      {
        return true;
      }
    }
    else
    {
      equal = false;
      break;
    }
  }
  return false;
}

// Function to execute command from history
char *extract_from_history(char *history[], int history_count, int index)
{
  if (index < 1 || index > history_count)
  {
    write(STDERR_FILENO, error_message, strlen(error_message));
    return NULL;
  }
  // printf("Executing from history: %s\n", history[index - 1]);
  return strdup(history[index - 1]);
}

int InputType(char Buffer[], char *history[], int history_count)
{
  // Checking for exit Condition.
  char e[] = {'e', 'x', 'i', 't'};
  if (StringCompare(Buffer, e))
  {
    return 0;
  }

  // Checking for Pipes + Output redirection
  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '|')
    {
      for (int j = i; Buffer[j] != '\0'; j++)
      {
        if (Buffer[j] == '>')
        {
          return 5;
        }
      }
      break;
    }
  }

  // Checking Both input and output redirection.
  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '<')
    {
      for (int j = i; Buffer[j] != '\0'; j++)
      {
        if (Buffer[j] == '>')
        {
          return 6;
        }
      }
      break;
    }
  }

  // Checking Pipes
  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '>')
    {
      return 1;
    }
  }

  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '<')
    {
      return 3;
    }
  }

  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '|')
    {
      return 4;
    }
  }

  // Else it is a simple command
  return 2;
}

void change_directory(char **Arguments)
{
  if (Arguments[0] != NULL && Arguments[1] != NULL && Arguments[2] == NULL)
  {

    if (chdir(Arguments[1]) == -1)
    {
      write(STDERR_FILENO, error_message, strlen(error_message));
      exit(0);
    }
  }
  else
  {
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(0);
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
      exit(0);
    }
    i++;
  }
}

void ExecutingCommand(char *command, char **Arguments)
{
  pid_t id = fork();
  if (id == 0)
  {
    if (strcmp(Arguments[0], "cd") == 0)
    {
      change_directory(Arguments);
    }
    else if (strcmp(Arguments[0], "path") == 0)
    {
      update_path(Arguments + 1);
    }
    else
    {
      int status_code = execvp(command, Arguments);
      if (status_code == -1)
      {
        write(STDERR_FILENO, error_message, strlen(error_message));
        exit(0);
      }
    }
  }
  else
  {
    wait(NULL);
  }
}

void Separation(char Buffer[], char *command, char ***Arguments)
{
  int j = 0;
  for (int i = 0; Buffer[i] != ' ' && Buffer[i] != '\0'; i++)
  {
    command[j] = Buffer[i];
    j++;
  }
  command[j] = '\0';

  j = 0;
  int index = 0;
  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == ' ')
    {
      index++;
    }
  }
  index = index + 2;

  *Arguments = (char **)malloc(sizeof(char *) * index);
  if (*Arguments == NULL)
  {
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(1);
  }

  char *dynamicArray;
  dynamicArray = strtok(Buffer, " ");
  int update = 0;
  while (dynamicArray != NULL)
  {
    char *tempO = (char *)malloc(strlen(dynamicArray) + 1);
    if (tempO == NULL)
    {
      write(STDERR_FILENO, error_message, strlen(error_message));
      exit(1);
    }
    strcpy(tempO, dynamicArray);
    (*Arguments)[update] = tempO;
    update++;
    dynamicArray = strtok(NULL, " ");
  }
  (*Arguments)[update] = NULL;
}

// To handel command with output redirections.
void Separation2(char Buffer[], char *command, char ***Arguments)
{
  char Buffer2[100];
  char Filename[100];
  int Append = 0; // Using an integer to represent boolean value
  int j = 0;
  for (int i = 0; Buffer[i] != '>'; i++)
  {
    Buffer2[i] = Buffer[i];
    j++;
  }
  Buffer2[j] = '\0';

  if (Buffer[j] == '>')
  {
    if (Buffer[j + 1] == '>')
    {
      Append = 1;
      j++;
    }
  }

  int index = 0;
  j = j + 2;
  for (int i = j; Buffer[i] != '\0'; i++)
  {
    Filename[index] = Buffer[i];
    index++;
  }
  Filename[index] = '\0';

  Separation(Buffer2, command, Arguments);

  // Now changing File Descriptor of stdout and calling open system call
  int fd;
  if (Append)
  {
    fd = open(Filename, O_CREAT | O_WRONLY | O_APPEND, 0666);
  }
  else
  {
    fd = open(Filename, O_CREAT | O_WRONLY, 0666);
  }

  int backup_fd = dup(1);
  dup2(fd, 1);
  close(fd);
  ExecutingCommand(command, *Arguments);
  close(1);
  dup2(backup_fd, 1);
  close(backup_fd);
}

void Separation3(char Buffer[], char *command, char ***Arguments)
{
  char Buffer2[100];
  char Filename[100];
  int j = 0;
  for (int i = 0; Buffer[i] != '<'; i++)
  {
    Buffer2[i] = Buffer[i];
    j++;
  }

  Buffer2[j] = '\0';

  int index = 0;
  j = j + 2;
  for (int i = j; Buffer[i] != '\0'; i++)
  {
    Filename[index] = Buffer[i];
    index++;
  }
  Filename[index] = '\0';
  Separation(Buffer2, command, Arguments);

  int fd = open(Filename, O_RDONLY);
  if (fd == -1)
  {
    perror("open");
    exit(EXIT_FAILURE);
  }
  int backup_fd = dup(0);
  dup2(fd, 0);
  close(fd);
  ExecutingCommand(command, *Arguments);
  close(0);
  dup2(backup_fd, 0);
  close(backup_fd);
}

int pipeCounter(char Buffer[])
{
  int counter = 0;
  for (int i = 0; Buffer[i] != '\0'; i++)
  {
    if (Buffer[i] == '|')
      counter++;
  }
  return counter;
}

void Pipe_Run(char *command, char **Arguments, int in, int out)
{
  pid_t pId = fork();

  if (pId < 0)
  { // If child process is not created.
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(EXIT_FAILURE);
  }
  else if (pId == 0)
  {
    if (in != 0)
    {
      dup2(in, 0);
      close(in);
    }

    if (out != 1)
    {
      dup2(out, 1);
      close(out);
    }

    int status_code = execvp(command, Arguments);

    if (status_code == -1)
    {
      write(STDERR_FILENO, error_message, strlen(error_message));
      exit(EXIT_FAILURE);
    }
  }
  else if (pId > 0)
  {
    wait(NULL);
  }
}

// This is used to handle only Piper type input
void Separation4(char Buffer[], char *command, char ***Arguments)
{
  char Buffer2[100];

  int Total_Pipes = pipeCounter(Buffer);
  // Now Total_Pipe contains the pipe present in the input

  int index = 0;
  int in;

  // The first process should get its input from the original file descriptor 0.
  in = 0;
  int z = 0;
  // Below loop will run for first n-1 command.
  int read = 0, fd[2];
  for (int i = 0; i < Total_Pipes; i++)
  {

    for (z = 0; Buffer[index] != '|' && Buffer[index] != '\0'; index++, z++)
    {
      Buffer2[z] = Buffer[index];
    }
    Buffer2[z] = '\0';
    index = index + 2;

    if (pipe(fd) == -1)
    {
      write(STDERR_FILENO, error_message, strlen(error_message));
      exit(EXIT_FAILURE);
    }
    Separation(Buffer2, command, Arguments);
    Pipe_Run(command, *Arguments, read, fd[1]);
    close(fd[1]);
    read = fd[0];
  }
  // This is the last command being run now.
  for (z = 0; Buffer[index] != '|' && Buffer[index] != '\0'; index++, z++)
  {
    Buffer2[z] = Buffer[index];
  }
  Buffer2[z] = '\0';
  index = index + 2;
  Separation(Buffer2, command, Arguments);
  Pipe_Run(command, *Arguments, read, 1);
}

// This is used to handel input which contain pipe along with output.
void PipeAndOutput(char Buffer[], char *command, char ***Arguments)
{
  char Buffer2[100], Filename[100];
  int index = 0, fd;
  int Append = 0; // Using an integer to represent boolean value
  for (int i = 0; Buffer[i] != '>'; i++)
  {
    Buffer2[i] = Buffer[i];
    index++;
  }
  if (Buffer[index + 1] == '>')
  {
    Append = 1;
  }
  index--;
  Buffer2[index] = '\0';
  index = index + 3;
  int j = 0;
  for (int i = index; Buffer[i] != '\0'; i++)
  {
    Filename[j] = Buffer[i];
    j++;
  }
  Filename[j] = '\0';
  if (Append)
  {
    fd = open(Filename, O_CREAT | O_WRONLY | O_APPEND, 0666);
  }
  else
  {
    fd = open(Filename, O_CREAT | O_WRONLY, 0666);
  }
  if (fd == -1)
  {
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(EXIT_FAILURE);
  }
  int backup_fd = dup(1);
  dup2(fd, 1);
  close(fd);
  Separation4(Buffer2, command, Arguments);
  close(1);
  dup2(backup_fd, 1);
  close(backup_fd);
}

void InputAndOutput(char Buffer[], char *command, char ***Arguments)
{
  char Buffer2[100], Filename[100];
  int index = 0, fd;
  int Append = 0; // Using an integer to represent boolean value
  for (int i = 0; Buffer[i] != '>'; i++)
  {
    Buffer2[i] = Buffer[i];
    index++;
  }
  if (Buffer[index + 1] == '>')
  {
    Append = 1;
  }
  index--;
  Buffer2[index] = '\0';

  if (Append)
    index++;
  index = index + 3;
  int j = 0;
  for (int i = index; Buffer[i] != '\0'; i++)
  {
    Filename[j] = Buffer[i];
    j++;
  }
  Filename[j] = '\0';

  if (Append)
  {
    fd = open(Filename, O_CREAT | O_WRONLY | O_APPEND, 0666);
  }
  else
  {
    fd = open(Filename, O_CREAT | O_WRONLY, 0666);
  }
  if (fd == -1)
  {
    write(STDERR_FILENO, error_message, strlen(error_message));
    exit(EXIT_FAILURE);
  }
  int backup_fd = dup(1);
  dup2(fd, 1);
  close(fd);
  Separation3(Buffer2, command, Arguments);
  close(1);
  dup2(backup_fd, 1);
  close(backup_fd);
}

// Function to add command to history
void add_to_history(char *history[], int *history_count, char *command)
{
  if (*history_count < MAX_HISTORY_SIZE)
  {
    history[(*history_count)++] = strdup(command);
  }
  else
  {
    // Shift history array to make space for new command
    free(history[0]);
    for (int i = 0; i < MAX_HISTORY_SIZE - 1; i++)
    {
      history[i] = history[i + 1];
    }
    history[MAX_HISTORY_SIZE - 1] = strdup(command);
  }
}

char *trim(char *str)
{
  while (isspace((unsigned char)*str))
  {
    str++;
  }
  if (*str == '\0')
  {
    return str;
  }
  char *end = str + strlen(str) - 1;
  while (end > str && isspace((unsigned char)*end))
  {
    end--;
  }
  *(end + 1) = '\0';
  return str;
}

void process_input(char *Buffer, char *history[], int *history_count)
{
  char command[100], **Arguments;
  // Add command to the history
  if (Buffer[0] != '!')
  {
    add_to_history(history, history_count, Buffer);
  }
  else
  {
    // Extraction From History
    int index = atoi(&Buffer[1]);
    char *extracted_command = history[index - 1];
    if (extracted_command != NULL)
    {
      strcpy(Buffer, extracted_command);
    }
  }

  char buffer_copy[100];
  strcpy(buffer_copy, Buffer);
  const char s[2] = "&";
  // Check for parallel commands

  // Loop to extract each command
  char *token_start = buffer_copy;
  for (int i = 0; buffer_copy[i] != '\0'; i++)
  {
    if (buffer_copy[i] == '&')
    {
      buffer_copy[i] = '\0'; // Null-terminate the command
      char *trimmed_command = trim(token_start);

      // Process the command
      if (strlen(trimmed_command) > 0)
      {
        int typeFlag = InputType(trimmed_command, history, *history_count);

        // If Input value is exit
        if (typeFlag == 0)
        {
          exit(0);
        }
        // Input contains Output Redirection
        if (typeFlag == 1)
        {
          // printf("Input contains Output Redirection\n");
          Separation2(trimmed_command, command, &Arguments);
        }
        // Input contains Input Redirection
        if (typeFlag == 3)
        {
          // printf("Input contains Input Redirection\n");
          Separation3(trimmed_command, command, &Arguments);
        }
        // Input contains Pipes
        if (typeFlag == 4)
        {
          // printf("Input contains Pipes\n");
          Separation4(trimmed_command, command, &Arguments);
        }
        // Simple command
        if (typeFlag == 2)
        {
          Separation(trimmed_command, command, &Arguments);
          ExecutingCommand(command, Arguments);
        }
        // Input contains Pipes + Output Redirection
        if (typeFlag == 5)
        {
          PipeAndOutput(trimmed_command, command, &Arguments);
        }
        // Input contains Input + Output Redirection
        if (typeFlag == 6)
        {
          InputAndOutput(trimmed_command, command, &Arguments);
        }
      }

      // Update token_start for the next command
      token_start = buffer_copy + i + 1;
    }
  }

  // Process the last command if it doesn't end with '&'
  char *trimmed_command = trim(token_start);
  if (strlen(trimmed_command) > 0)
  {
    int typeFlag = InputType(trimmed_command, history, *history_count);

    // If Input value is exit
    if (typeFlag == 0)
    {
      exit(0);
    }
    // Input contains Output Redirection
    if (typeFlag == 1)
    {
      // printf("Input contains Output Redirection\n");
      Separation2(trimmed_command, command, &Arguments);
    }
    // Input contains Input Redirection
    if (typeFlag == 3)
    {
      // printf("Input contains Input Redirection\n");
      Separation3(trimmed_command, command, &Arguments);
    }
    // Input contains Pipes
    if (typeFlag == 4)
    {
      // printf("Input contains Pipes\n");
      Separation4(trimmed_command, command, &Arguments);
    }
    // Simple command
    if (typeFlag == 2)
    {
      Separation(trimmed_command, command, &Arguments);
      ExecutingCommand(command, Arguments);
    }
    // Input contains Pipes + Output Redirection
    if (typeFlag == 5)
    {
      PipeAndOutput(trimmed_command, command, &Arguments);
    }
    // Input contains Input + Output Redirection
    if (typeFlag == 6)
    {
      InputAndOutput(trimmed_command, command, &Arguments);
    }
  }
}

int main(int argc, char *argv[])
{
  /* Shell initialization */
  // initialize();
  char *history[MAX_HISTORY_SIZE];
  int history_count = 0;
  // Batch Mode
  if (argc == 2)
  {
    FILE *file = fopen(argv[1], "r");
    if (file == NULL)
    {
      perror("Error opening file");
      return 1;
    }
    char Buffer[100];
    while (fgets(Buffer, sizeof(Buffer), file))
    {
      // Process each line from the file
      process_input(Buffer, history, &history_count);
    }
    fclose(file);
    return 0;
  }

  // Interactive Mode
  //  Taking Input
  while (1)
  {
    char Buffer[100], command[100], **Arguments;
    // printDir();
    printf("wish> ");
    fgets(Buffer, 100, stdin);
    if (Buffer[strlen(Buffer) - 1] == '\n') // Remove newline character
      Buffer[strlen(Buffer) - 1] = '\0';
    // Add command to the history
    if (Buffer[0] != '!')
    {
      add_to_history(history, &history_count, Buffer);
    }
    else
    {
      // Extraction From History
      int index = atoi(&Buffer[1]);
      char *extracted_command = extract_from_history(history, history_count, index);
      if (extracted_command != NULL)
      {
        strcpy(Buffer, extracted_command);
        free(extracted_command);
      }
    }
    // Use a copy of Buffer for tokenization
    char buffer_copy[100];
    strcpy(buffer_copy, Buffer);
    const char s[2] = "&";
    // Check for parallel commands

    // Loop to extract each command
    char *token_start = buffer_copy;
    for (int i = 0; buffer_copy[i] != '\0'; i++)
    {
      if (buffer_copy[i] == '&')
      {
        buffer_copy[i] = '\0'; // Null-terminate the command
        char *trimmed_command = trim(token_start);

        // Process the command
        if (strlen(trimmed_command) > 0)
        {
          int typeFlag = InputType(trimmed_command, history, history_count);

          // If Input value is exit
          if (typeFlag == 0)
          {
            exit(0);
          }
          // Input contains Output Redirection
          if (typeFlag == 1)
          {
            // printf("Input contains Output Redirection\n");
            Separation2(trimmed_command, command, &Arguments);
          }
          // Input contains Input Redirection
          if (typeFlag == 3)
          {
            // printf("Input contains Input Redirection\n");
            Separation3(trimmed_command, command, &Arguments);
          }
          // Input contains Pipes
          if (typeFlag == 4)
          {
            // printf("Input contains Pipes\n");
            Separation4(trimmed_command, command, &Arguments);
          }
          // Simple command
          if (typeFlag == 2)
          {
            Separation(trimmed_command, command, &Arguments);
            ExecutingCommand(command, Arguments);
          }
          // Input contains Pipes + Output Redirection
          if (typeFlag == 5)
          {
            PipeAndOutput(trimmed_command, command, &Arguments);
          }
          // Input contains Input + Output Redirection
          if (typeFlag == 6)
          {
            InputAndOutput(trimmed_command, command, &Arguments);
          }
        }

        // Update token_start for the next command
        token_start = buffer_copy + i + 1;
      }
    }

    // Process the last command if it doesn't end with '&'
    char *trimmed_command = trim(token_start);
    if (strlen(trimmed_command) > 0)
    {
      int typeFlag = InputType(trimmed_command, history, history_count);

      // If Input value is exit
      if (typeFlag == 0)
      {
        exit(0);
      }
      // Input contains Output Redirection
      if (typeFlag == 1)
      {
        // printf("Input contains Output Redirection\n");
        Separation2(trimmed_command, command, &Arguments);
      }
      // Input contains Input Redirection
      if (typeFlag == 3)
      {
        // printf("Input contains Input Redirection\n");
        Separation3(trimmed_command, command, &Arguments);
      }
      // Input contains Pipes
      if (typeFlag == 4)
      {
        // printf("Input contains Pipes\n");
        Separation4(trimmed_command, command, &Arguments);
      }
      // Simple command
      if (typeFlag == 2)
      {
        Separation(trimmed_command, command, &Arguments);
        ExecutingCommand(command, Arguments);
      }
      // Input contains Pipes + Output Redirection
      if (typeFlag == 5)
      {
        PipeAndOutput(trimmed_command, command, &Arguments);
      }
      // Input contains Input + Output Redirection
      if (typeFlag == 6)
      {
        InputAndOutput(trimmed_command, command, &Arguments);
      }
    }
  }

  return 0;
}
