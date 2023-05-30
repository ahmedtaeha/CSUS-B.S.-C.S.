# ahmed taeha
# section 1
# wizard final##


.global	_start
.intel_syntax noprefix

.section .text

rollDice:	

	call	Random
	add		rdx, 1

	ret	

printPlayerName:
	cmp		rdx, 0
	jne		next1

	lea		rdx, wizard1
	call	PrintZString
next1:
	cmp		rdx, 1
	jne		next2

	lea		rdx, wizard2
	call	PrintZString
next2:
	cmp		rdx, 2
	jne		next3

	lea		rdx, wizard3
	call	PrintZString
next3:
	cmp		rdx, 3
	jne		next4

	lea		rdx, wizard4
	call	PrintZString
next4:
	cmp		rdx, 4
	jne		next5

	lea		rdx, wizard5
	call	PrintZString
next5:
	cmp		rdx, 5
	jne		next6

	lea		rdx, wizard6
	call	PrintZString
next6:
	cmp		rdx, 6
	jne		next7

	lea		rdx, wizard7
	call	PrintZString
next7:
	cmp		rdx, 7
	jne		next8

	lea		rdx, wizard8
	call	PrintZString
next8:
	cmp		rdx, 8
	jne		next9

	lea		rdx, wizard9
	call	PrintZString
next9:
	cmp		rdx, 9
	jne		next10

	lea		rdx, wizard10
	call	PrintZString
next10:
	ret

askForTarget:

	sub	rsp, 40	
	mov	QWORD PTR [rsp], rdi	
	mov	DWORD PTR 8[rsp], esi	

	mov	DWORD PTR 28[rsp], 0	
	mov	esi, DWORD PTR [rsp]	
	lea	rdi, mPlayer[rip]	
	mov		rdx, rdi
	call	PrintZString

	movsx	rdx,DWORD PTR [rsp]
	call	PrintInt

	call	printPlayerName

	mov		dl, '\n'
	call	PrintChar

	mov		esi, DWORD PTR 4[rsp]	

	lea		rdx, mhealth[rip]	
	call	PrintZString

	movsx	rdx, DWORD PTR 4[rsp]
	call	PrintInt

	mov		dl, '\n'
	call	PrintChar

	lea	rdi, mTarget[rip]	
	mov	eax, 0	
	mov rdx, rdi
	call	PrintZString

	lea	rsi, 28[rsp]	
	lea	rdi, intFormat[rip]	
	mov	eax, 0	

	mov		rdx, 2
	call	SetForeColor

	call ScanInt
	mov 28[rsp], rdx
	mov	target, rdx

	mov		rdx, 7
	call	SetForeColor

	mov	eax, DWORD PTR 28[rsp]	
	add	rsp, 40	
	ret	
	
hitTarget:

	push	rbx	
	mov	ebx, edi	

	lea		rdx, spells
	call	PrintZString

	mov		rdx, 3
	call	SetForeColor

	call	ScanInt
	mov		spell, rdx

	mov		rdx, 7
	call	SetForeColor

	mov		rdx, spell
	cmp		rdx, 3
	jne		Normal
	
	call	rollDice
	cmp		rdx, 7
	jne		Normal

	mov		rdx, 25
	jmp		Attack

Normal:
	mov		eax, 14
	add		eax, edx
	mul		edx
	mov		rdx, rax
	call	rollDice

	cmp		rdx, 11
	jge		Attack

	cmp		rdx, 7
	jle		Attack
	mov		rdx, 0

Attack:
	mov	rax, rdx
	lea	rcx, players[rip]	
	movsx	rdi, ebx	
	lea	rdx, [rdi+rdi]	
	lea	rsi, [rdx+rdi]	
	mov	edx, DWORD PTR 4[rcx+rsi*4]	
	sub	edx, eax	
	mov	DWORD PTR 4[rcx+rsi*4], edx

	cmp	rax, 0
	jne esc

NoDamage:

	lea		rdx, missed
	call PrintZString

esc:
	pop	rbx	
	ret	

_start:

	push	r13	
	push	r12	
	push	rbp	
	push	rbx	
	
	sub	rsp, 24	

	lea	rdx, me
	call	PrintZString

	lea rdx, wizard
	call PrintZString
	
	lea	rdi, prompt[rip]	
	mov	eax, 0	
	mov rdx, rdi
	call	PrintZString	

	lea	rsi, 12[rsp]	
	lea	rdi, intFormat[rip]
	mov	eax, 0

	mov		rdx, 1
	call	SetForeColor

	call	ScanInt
	mov		noOfplayers, edx
	mov		12[rsp], rdx

	mov		rdx, 7
	call	SetForeColor

	mov	edx, 0	

	jmp	checkIfDone	

initialize: #initializing players with 100 Health 

	lea		rsi, players[rip]
	movsx	rcx, edx	
	lea		rax, [rcx+rcx]	
	lea		rdi, [rax+rcx]	
	mov		DWORD PTR [rsi+rdi*4], edx	

	mov	DWORD PTR 4[rsi+rdi*4], 100	
	mov	DWORD PTR 8[rsi+rdi*4], 0	

	add	edx, 1	

checkIfDone: #check if all the players are initialized

	mov	r12d, DWORD PTR 12[rsp]	
	cmp	r12d, edx	
	jg	initialize	

cbreak:	#check the number of surviving players if 1 break the loop

	cmp	r12d, 1	
	jle	resetIndex	

	mov	ebx, 0	
	jmp	cwhile	

outOfBound:	#this section handle the invladi index of player

	lea	rdi, mInvalid1[rip]	
	mov rdx, rdi
	call	PrintZString

	sub	ebx, 1	

incrementCounter: #incrementing loop counter

	add	ebx, 1	

cwhile:	#whilie surviving players >= 2

	cmp	DWORD PTR 12[rsp], ebx	
	jle	cbreak	

	movsx	rax, ebx	
	lea	rdx, [rax+rax*2]	

	lea	rax, players[rip]	
	cmp	DWORD PTR 4[rax+rdx*4], 0	
	jle	incrementCounter	

	lea	rax, players[rip]	
	lea	rax, [rax+rdx*4]	
	mov	rdi, QWORD PTR [rax]	
	mov	esi, DWORD PTR 8[rax]	
	call	askForTarget	
	mov	ebp, eax	

	cmp	DWORD PTR 12[rsp], eax	
	jle	outOfBound

	cmp	ebx, eax	
	je	hittingOneself

	cmp	eax, 0
	jl	outOfBound

	cdqe
	lea	rdx, [rax+rax*2]	

	lea	rax, players[rip]	
	cmp	DWORD PTR 4[rax+rdx*4], 0	
	jle	alreadyDead	

	mov	edi, ebp	
	call	hitTarget	
	mov	r13d, eax	

	mov	esi, eax	
	lea	rdi, mPoints[rip]	
	mov	eax, 0	

	mov		rdx, rdi
	call	PrintZString

	mov		rdx, 6
	call	SetForeColor

	movsx		rdx, esi	
	call		PrintInt

	mov		rdx, 7
	call	SetForeColor

	lea		rdi, mmPoint[rip]
	mov		rdx, rdi
	call	PrintZString

	mov 	dl, '\n'
	call	PrintChar


	lea	rdx, players[rip]	
	movsx	rcx, ebx	
	lea	rax, [rcx+rcx]	
	lea	rsi, [rax+rcx]	
	add	r13d, DWORD PTR 8[rdx+rsi*4]	
	mov	DWORD PTR 8[rdx+rsi*4], r13d	

	movsx	rax, ebp	
	lea	rax, [rax+rax*2]	

	cmp	DWORD PTR 4[rdx+rax*4], 0	
	jg	incrementCounter	

	lea		rdx, splayer[rip]
	call	PrintZString

	movsx		rdx, ebp
	call	PrintInt

	mov	esi, ebp	
	lea	rdi, mPlayerDead[rip]	
	mov	eax, 0	
	mov rdx, rdi
	call	PrintZString	

	lea		rdx, dead
	call	PrintZString

	sub	r12d, 1	
	jmp	incrementCounter	

hittingOneself: #check if user is trying to hit himself

	lea	rdi, mInvalid2[rip]	
	mov rdx, rdi
	call	PrintZString

	sub	ebx, 1	
	jmp	incrementCounter	

alreadyDead:	#this section runs when user wanted to hit player that is dead already (input validation)

	mov	esi, ebp	
	lea	rdi, mInvalid3[rip]	
	mov	eax, 0	
	mov rdx, rdi
	call	PrintZString	

	sub	ebx, 1	
	jmp	incrementCounter

resetIndex:	#reseting index of players so we can loop through all players and find the winner

	mov	esi, 0	

findWinner:

	cmp	DWORD PTR 12[rsp], esi	
	jle	escape	

	movsx	rax, esi	
	lea	rdx, [rax+rax*2]	

	lea	rax, players[rip]	
	cmp	DWORD PTR 4[rax+rdx*4], 0	
	jg	found	

	add	esi, 1	
	jmp	findWinner

found:	#when player of Health >0 found display the winner message and ascii art

	lea	rdx, splayer[rip]
	call PrintZString

	mov		rdx, 0

	mov		edx, esi
	call	PrintInt

	lea	rdi, mPlayerWon[rip]	
	mov	eax, 0	
	mov rdx, rdi
	call	PrintZString

	lea	rdx, WinnerAscii
	call	PrintZString

escape: #return safe

	Call Exit
	ret	

.section .data

target:		.quad	0
spell:		.quad   0
noOfplayers:.quad   0

mnewLine:	.string "\n"
mPlayer:	.string	"PLAYER "
mhealth:	.string	"Health: "
mTarget:	.string	"Your target: "
intFormat:	.string	"%d"
prompt:		.string	"How many players? "
mInvalid1:	.string	"\n\nInvalid Target\ntry again\n\n"
mInvalid2:	.string	"\n\nYou can't hit yourself\ntry again\n\n"
mInvalid3:	.string	"\n\nPlayer is dead already\ntry again\n\n"
mPoints:	.string	"\nSpell blasts them for "
mmPoint:	.string " points\n\n"
splayer:	.string "Player "
mPlayerDead:.string	" is dead!\n\n"
mPlayerWon:	.string	" won!!\n\n"
me:
	.ascii 	"Ahmed Taeha \n\0"
missed:
	.ascii "\nAttack is Missed\n\0"

spells:
	.ascii	"\n\n1. Cast Acid Splash\n"
	.ascii  "2. Blade Ward\n"
	.ascii  "3. True Strike\n"
	.ascii	"4. Cast Deterioration Hex\n\n"
	.ascii	"Option: \0"

wizard1:
	.ascii " Barry Winkle         \0"
wizard2:
	.ascii " Nicolas Flamel	      \0"
wizard3:
	.ascii " Armando Dippet       \0"
wizard4:
	.ascii " Trolly Witch	      \0"
wizard5:
	.ascii " Faris Spavin	      \0"
wizard6:
	.ascii " Musidora Barkwith    \0"
wizard7:
	.ascii " Stoddard Withers     \0"
wizard8:
	.ascii " Newton Scamander     \0"
wizard9:
	.ascii " Harry Potter         \0"
wizard10:
	.ascii " Elphias Doge	      \0"

wizard:
	.ascii  "\n\nWELCOME TO WIZARDS GAME\n\n"
	.ascii	"\n\n	              .\n"
	.ascii	"\n"
	.ascii	"                   .\n"
	.ascii	"         /^\\     .\n"
	.ascii	"    /\\   \"V\"\n"
	.ascii	"   /__\\   I      O  o\n"
	.ascii	"  //..\\\\  I     .\n"
	.ascii	"  \\].`[/  I\n"
	.ascii	"  /l\/j\\  (]    .  O\n"
	.ascii	" /. ~~ ,\\/I          .\n"
	.ascii	" \\\\L__j^\\/I       o\n"
	.ascii	"  \\/--v}  I     o   .\n"
	.ascii	"  |    |  I   _________\n"
	.ascii	"  |    |  I c(`       ')o\n"
	.ascii	"  |    l  I   \.     ,//\n"
	.ascii	"_/j  L l\\_!  _//^---^\\\\_   \n\n\0"

dead:
	.ascii "	%%% %%%%%%%            |#|\n"
	.ascii "    %%%% %%%%%%%%%%%        |#|####\n"
	.ascii "  %%%%% %         %%%       |#|=#####\n"
	.ascii " %%%%% %   @    @   %%      | | ==####\n"
	.ascii "%%%%%% % (_  ()  )  %%     | |    ===##\n"
	.ascii "%%  %%% %  \_    | %%      | |       =##\n"
	.ascii "%    %%%% %  u^uuu %%     | |         ==#\n"
	.ascii "      %%%% %%%%%%%%%      | |           V\n\n\0"

WinnerAscii:
	.ascii "\n\n	⠀⠀⠀⠀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠹⣷⣀⠀⠀⢰⣿⣿⡆⠀⠀⣀⣾⠏⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⢷⣦⣄⠙⠋⣀⣴⡾⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡿⢿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⡇⢸⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠇⠸⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n"
	.ascii "⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀\n"
	.ascii "⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀ \n"
	.ascii " ⠈⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉  \n\n\0"

.section .bss

players:	.zero	1200	
