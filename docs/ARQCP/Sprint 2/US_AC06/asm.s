.section .note.GNU-stack,"",@progbits	# remove o aviso "missing .note.GNU-stack section"

.section .text

	.global extract_token 	# define a função 'extract_token'
	
	# parâmetros e registos:
	# char* input em %rdi
	# char* token em %rsi
	# int* output em %rdx
	
extract_token:
	pushq %rbp
	movq %rsp, %rbp
	 
	movq %rdi, %r8          # Coloca o *input em r8
    movq %rsi, %r9          # Coloca o *token em r9
    movq %rdx, %rcx         # Coloca o *output em rcx

	movq (%rcx), %rax       # Coloca o *output em rax

compare_strings:
	cmpb $0, (%r8)          # verifica se o *input é nulo
	je end_of_string        # if equals, jump para 'end_of_string'
    movzbq (%r8), %rax      # Move o byte atual de *input para %rax
    movzbq (%r9), %rcx      # Move o byte atual de *output para %rcx
    cmp %cl, %al            # Compara os bytes apontados por *input e *output
    je equals               # if equals, jump para 'equals'

    incq %r8                # incrementa *input para apontar para o próximo valor
    movq %rsi, %r9			# restaura o valor inicial de r9
    jmp compare_strings     # jump para 'compare_strings'

equals:
    incq %r8                # incrementa *input para apontar para o próximo valor
    incq %r9                # incrementa *token para apontar para o próximo valor
    cmpb $0, (%r9)          # verifica se chegou ao final de *token
    je token_end            # if equals jump para 'token_end'
    jmp compare_strings     # jump para 'compare_strings'

token_end:
    xor %ecx, %ecx          # limpa o ecx

convert_value:
    movb (%r8), %al         # Coloca o *input em al
    cmpb $0, %al			# compare
    je done					# if eqquals jump para 'done'
    
    cmpb $65 , %al			# Verifica se o caractere em al (*input) não é um número pelo código ascii
    jge letter_find			# if greater or equal, jump para 'letter_find'
    
    cmpb $'#', %al          # verifica se o caracter em al (*input) é '#'
    je done                 # jump para 'done'
    cmpb $'.', %al          # verifica se o caracter em al (*input) é '.'
    je decimal              # jump para 'decimal' se for igual

	movzbl %al, %eax		# coloca o *input em eax
    subl $'0', %eax         # converte o caractere atual para um número inteiro 
    imull $10, %ecx         # Multiplica o valor em *output por 10
    addl %eax, %ecx         # Adiciona o valor em *input ao valor em *output
    incq %r8                # faz *input apontar para o próximo valor
    jmp convert_value       # jump para 'convert_value'

decimal:
    incq %r8                # incrementa *input para pular '.'
    jmp convert_value       # jump para 'convert_value'

done:
    movl %ecx, (%rdx)       # Coloca o *output em rdx
    xor %rax, %rax
    movq $1, %rax
    
	movq %rbp, %rsp
	popq %rbp
	
    ret                     
   
end_of_string:
	movq %rbp, %rsp
	popq %rbp
	
	xor %rax, %rax
	
	ret
	
letter_find:
	movq $0, %rdx
	
	movq %rbp, %rsp
	popq %rbp
	
	ret
