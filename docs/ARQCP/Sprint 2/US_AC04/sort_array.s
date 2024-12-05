.section .text
	.global sort_array
	
sort_array:
    movq $0, %rcx				#Contador externo = 0
    movl (%rdi,%rcx,4), %r8d	#Vai buscar o 1º elemento do array (%rdi - pointer do array, rcx - contador/posição, 4- inteiro)

fora_loope:
    cmpq %rsi, %rcx				#compara o contador externo com o tamanho do array 
    je finalee					#se = salta
    
    movq %rcx, %rdx				#guarda o valor em rdx
    incq %rdx					#incrementa o contador externo

    jmp dentro_loope

dentro_loope:
    cmpq %rsi, %rdx				#compara o tamanho do array com o valor do contador externo que foi previamente guardado em rdx
    je aumenta_fora				#se =, aumenta o contador externo

    movl (%rdi, %rdx, 4),%r9d	#vai buscar o proximo elemento do array e guarda em %r9d

    cmpl %r8d, %r9d				#compara o elemento atual (%r8d)com o próximo (%r9d)
    jl troca					#r9d < r8d, salta
    
    jmp aumenta_dentro

troca:
    movl %r8d, %r10d			#troca os valores dos elementos com a ajuda de r10d
    movl %r9d, %r8d
    movl %r10d,%r9d
    
    movl %r9d, (%rdi, %rdx, 4)	#vai buscar o proximo elemento do array
    
    jmp aumenta_dentro

aumenta_dentro:
    incq %rdx					#aumenta o contador interno
    jmp dentro_loope

aumenta_fora:
    movl %r8d, (%rdi, %rcx, 4)	#guarda o valor da posição atual
    incq %rcx					#aumenta o contador externo
    movl (%rdi, %rcx, 4), %r8d	#guarda o valor do proximo elemento do array em %r8d
    jmp fora_loope

finalee:
    ret

