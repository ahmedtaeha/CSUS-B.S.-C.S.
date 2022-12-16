.global	_start
.intel_syntax noprefix

.section .text

menu:

    lea     rdx, promptBill
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanInt
    mov     bill, rdx

    mov     rdx, 7
    call    SetForeColor
    
whileInvalid:
    lea     rdx, promptPeople
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanInt
    mov     split, rdx

    mov     rdx, 7
    call    SetForeColor

    mov     rax, split

    cmp     rax, 0
    jle     whileInvalid

    lea     rdx, mOutput1
    call    PrintZString

    mov     rdx, 0

    mov     rax, bill
    mov     rbx, split
    div     rbx

    mov     rdx, 1
    call    SetForeColor

    mov     rdx, rax
    call    PrintInt

    mov     rdx, 7
    call    SetForeColor

    lea     rdx, mOutput2
    call    PrintZString

    ret


_start:

    mov     rdx,  7
    call    SetForeColor

    call    menu

    call Exit

.section .data

bill:     .quad   0
split:    .quad   0


promptBill:
    .ascii  "\nHow much in knuts, was the bill?\n\0"
promptPeople:
    .ascii  "\nHow many people are splitting the bill?\n\0"
mOutput1:
    .ascii  "\nOkay, witches and wizards, give \0"
mOutput2:
    .ascii " knuts each.\n\0"
    
