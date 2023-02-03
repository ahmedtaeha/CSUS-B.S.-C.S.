# Ahmed Taeha
# Lab 6

.global _start
.section .text
.intel_syntax noprefix

guessMe:

    mov     rdx, 100
    call    Random
    mov     secret, rdx

    cwhile:
        lea     rdx, mGuess
        call    PrintZString

        mov     rdx, 6
        call    SetForeColor

        call    ScanInt
        mov     rax, rdx

        mov     rdx, 7
        call    SetForeColor

        cmp     rax, secret
        je      cbreak

        cmp     rax, secret
        jg      tooHigh

        lea     rdx, mTooLow
        call    PrintZString

        jmp     cwhile

        tooHigh:
            lea     rdx, mTooHigh
            call    PrintZString
            jmp     cwhile

    cbreak:
        lea     rdx, mOutroMsg
        call    PrintZString

    ret

_start:
    mov     rdx, 7
    call    SetForeColor

    call    guessMe

    call Exit

.section .data

secret:
    .quad   0

greetings:
    .ascii  "\nGreetings Ravenclaw student!\nBefore you may proceed... Within 1 and 100, a number I need\n\n\0"
mGuess:
    .ascii  "Guess: \0"
mTooHigh:
    .ascii  "Alas,  that is too large\n\n\0"
mTooLow:
    .ascii  "Regrettably, that is too small\n\n\0"
mOutroMsg:
    .ascii  "Well-reasoned! You may now enter!\n\n\0"
