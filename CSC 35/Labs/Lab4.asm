# Ahmed Taeha
# Lab 4

.global	_start
.intel_syntax noprefix

.section .text

checkStress:
    mov     rsi, 0
    cwhile:

        mov     rdx, Questions[rsi]
        call    PrintZString

        mov     rdx, 6
        call    SetForeColor

        call    ScanInt
        mov     rax, rdx

        mov     rdx, 7
        call    SetForeColor

        mov     rcx, GoodAnswers[rsi]
        cmp     rax, rcx
        jne     continue

        mov     rcx, Points[rsi]
        add     total, rcx

    continue:

        add     rsi, 8

        cmp     rsi, 40
        jl      cwhile

    lea     rdx, mTotal
    call    PrintZString

    mov     rdx, 2
    call    SetForeColor

    mov     rdx, total
    call    PrintInt

    mov     rax, total
    cmp     rax, 50
    jge     isStressed

    lea     rdx, mFine
    call    PrintZString

    jmp     escape

isStressed:
    lea     rdx, mStressed
    call    PrintZString


escape:

    mov     rdx, 7
    call    SetForeColor

    ret

_start:

    mov     rdx,  7
    call    SetForeColor

    call    checkStress

    call Exit

.section .data

option:     .quad   0

total:  .quad 0

mTotal:
    .ascii  "\nYour total stress level is at \0"
mStressed:
    .ascii  "\n\nOh dear! You are so stressed! Here! Have some magical calming hot cocoa!\n\n\0"
mFine:
    .ascii  "\n\nYou are going do fine student! Keep your chin up!\n\n\0"
    
Question1:
    .ascii  "Did you get an animal pet? (1=yes, 0=no)?\n\0"
Question2:
    .ascii  "Are you afraid  of waht house you will be sorted into? (1=yes, 0=no)?\n\0"
Question3:
    .ascii  "Are you a muggle-born? (1=yes, 0=no)?\n\0"
Question4:
    .ascii  "Oh, did you get a wand yet? (1=yes, 0=no)?\n\0"
Question5:
    .ascii  "Are you getting enough speed? (1=yes, 0=no)?\n\0"

Questions:
    .quad   Question1
    .quad   Question2
    .quad   Question3
    .quad   Question4
    .quad   Question5

GoodAnswers:
    .quad   0
    .quad   1
    .quad   1
    .quad   0
    .quad   1

Points:
    .quad   15
    .quad   30
    .quad   30
    .quad   25
    .quad   20
