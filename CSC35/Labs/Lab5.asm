# Ahmed Taeha
# Lab 5

.global	_start
.intel_syntax noprefix

.section .text

findRole:

    lea     rdx, mWelcome
    call    PrintZString

    lea     rdx, Question1
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanChar
    mov     al, dl

    mov     rdx, 7
    call    SetForeColor

    cmp     al, 121
    jne      left
    
    lea     rdx, Question3
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanChar
    mov     al, dl

    mov     rdx, 7
    call    SetForeColor

    cmp      al, 121
    je      init
    jmp     cont

left:

    lea     rdx, Question2
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanChar
    mov     al, dl

    mov     rdx, 7
    call    SetForeColor

    cmp     al, 121
    je      sen
    jmp     due

sen:
    mov     rdx, 2
    call    SetForeColor

    lea     rdx, Sentinels
    call    PrintZString
    jmp     escape

due:
    mov     rdx, 2
    call    SetForeColor

    lea     rdx, Duelists
    call    PrintZString
    jmp     escape

init:
    mov     rdx, 2
    call    SetForeColor

    lea     rdx, Initiators
    call    PrintZString
    jmp     escape

cont:
    mov     rdx, 2
    call    SetForeColor

    lea     rdx, Controllers
    call    PrintZString
    jmp     escape


escape:

    mov     rdx, 7
    call    SetForeColor

    ret

_start:

    mov     rdx,  7
    call    SetForeColor

    call    findRole

    call Exit

.section .data

option:     .quad   0

mWelcome:
    .ascii "\nWelcome to Valorant!\nLet's find out which Valorant's role will suit you\n\n\0"
    
Question1:
    .ascii  "Do you believe in team work? (y=yes, n=no)?\n\0"
Question2:
    .ascii  "What is your gameplay style? Do you lurk most of the time? (y=yes, n=no)?\n\0"
Question3:
    .ascii  "Do you check every corner? (1=yes, 0=no)?\n\0"

Controllers:
    .ascii  "Controllers!\n\0"
Duelists:
    .ascii  "Duelists!\n\0"
Sentinels:
    .ascii  "Sentinels!\n\0"
Initiators:
    .ascii  "Initiators!\n\0"

