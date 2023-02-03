# Ahmed Taeha
# Lab 3

.global	_start
.intel_syntax noprefix

.section .text

menu:
    mov     rsi, 0
    cwhile:

        mov     rdx, Names[rsi]
        call    PrintZString

        add     rsi, 8

        cmp     rsi, 40
        jl      cwhile

    lea     rdx, prompt
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanInt
    mov     option, rdx

    mov     rdx, 7
    call    SetForeColor

    lea     edx, selected
    call    PrintZString

    mov     rax, option
    cmp     rax, 0
    jle     invalid

    cmp     rax, 5
    jg      invalid

    mov     rcx, 8
    sub     rax, 1

    imul    rcx
    mov     rsi, rax

    mov     rdx, Names[rsi]
    call    PrintZString

    call    shop

    jmp     escape

invalid:
    mov     rdx, mInvalid
    call    PrintZString

escape:
    ret

shop:

    lea     rdx, promptKnuts
    call    PrintZString

    mov     rdx, 6
    call    SetForeColor

    call    ScanInt
    mov     rbx, rdx

    mov     rdx, 7
    call    SetForeColor

    mov     rax, option
    mov     rcx, 8
    sub     rax, 1

    imul    rcx
    mov     rsi, rax

    mov     rax, Costs[rsi]

    cmp     rbx, rax
    jl      insuf

    sub     rbx, rax

    mov     rdx,  1
    call    SetForeColor

    mov     rdx,  rbx
    call    PrintInt

    mov     rdx,  7
    call    SetForeColor

    lea     rdx, mChange
    call    PrintZString

    jmp     go
insuf:
    lea     rdx, notEnough
    call    PrintZString

go:
    ret


_start:

    mov     rdx,  7
    call    SetForeColor

    call    menu

    call Exit

.section .data

option:     .quad   0


prompt:
    .ascii  "\n\nEnter your selection: \0"
selected:
    .ascii  "You selected:\n\0"
mInvalid:
    .ascii  "Invalid Selection\n\n\0"
notEnough:
    .ascii "You don't have enough money to buy the selected item\n\n\0"

promptKnuts:
    .ascii "\n\nHow many knuts are you feeding it?\n\0"
mChange:
    .ascii " knuts is your change\n\n\0"
    
Bertie:
    .ascii  "1. Bertie Bott's Every Flavor Sliders (23 kunts)\n\0"
Cake:
    .ascii  "2. Cauldron Cakes  (37 kunts)\n\0"
Pumpkin:
    .ascii  "3. Pumpkin Pasties (74 kunts)\n\0"
Baskin:
    .ascii  "4. Baskin Robin Ice-cream (53 kunts)\n\0"
Cancel:
    .ascii  "5. Cancel the order (0 kunts)\n\0"

Names:
    .quad   Bertie
    .quad   Cake
    .quad   Pumpkin
    .quad   Baskin
    .quad   Cancel

Costs:
    .quad   23
    .quad   37
    .quad   74
    .quad   53
    .quad   0
