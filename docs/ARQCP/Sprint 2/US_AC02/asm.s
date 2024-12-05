.section .note.GNU-stack,"",@progbits	#removes the warning " missing .note.GNU-stack section"

.section .text 
	.global enqueue_value
	
	# parâmetros:
	# int * array em rdi
	# int length em esi
	# int * read em rdx
	# int * write em rcx
	# int value em r8d

enqueue_value:

	# prologue
    pushq %rbp             
    movq %rsp, %rbp        

    movl (%rcx), %r10d     				# Copia o valor em rcx e coloca em r10d (write )
    imull $4, %r10d        				# obtém o deslocamento de r10d
    
    movslq %r10d, %r10  				# estende o sinal de r10d para r10
    addq %r10, %rdi 					# adiciona o deslocamento ao array
    movl %r8d, (%rdi) 					# coloca o valor na posiçao correta do array
    
    addl $1, (%rcx) 					# incrementa *write
    
    pushq %rdx 							# guarda o valor de *read na stack
    
    movl (%rcx), %eax             		# coloca *write em eax
    cltd                          		# estende o sinal do valor em eax
    idivl %esi                    		# divide o valor em eax (*write) pelo comprimento do array
    movl %edx, (%rcx)             		# armazena o resto da divisão em *write     

    pop %rdx                      		# restaura o valor inicial de *read

    movl (%rdx), %r10d             		# copia *read para r9d
    cmp %r10d, (%rcx)              		# compara *write com *read
    je is_empty              			# se a condição for correspondida, pula para 'is_empty'

    jmp end                       		# jump incondicional para is_empty

is_empty:

    addl $1, (%rdx)               		# incrementa *read para apontar para o próximo valor no array

    pushq %rdx                   		# guarda o valor de *read na stack

    movl (%rdx), %r9d             		# coloca *read em r9d
    cltd                          		# estende o sinal do valor em eax
    idivl %esi                    		# divide o valor em eax pelo comprimento
    movl %edx, %r9d               		# armazena o valor do resto em r9d

    popq %rdx                     		# restaura o valor inicial de *read

    movl %r9d, (%rdx)             		# copia o valor em r9d para rdx (resto)

end:
	
	# epilogue
    mov %rbp, %rsp        
    pop %rbp     
             
    ret     
