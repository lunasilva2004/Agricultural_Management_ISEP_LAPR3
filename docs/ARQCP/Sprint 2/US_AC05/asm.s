.section .text
    .global mediana

mediana:
    movq %rsi, %r8
    movq $0, %rcx
    movq %rsi, %r9
    movq $0, %r10
    movq $0, %r11
    movq $0, %rax

    cmpq $0, %rsi
    je end

    jmp array_sort

array_sort:

  outer_loop:

    cmp $0, %r8
    je continue

	inner_loop:
		cmpq $0, %r9
		je next_outer

		movl (%rdi, %r10, 4), %eax
		movl (%rdi, %r11, 4), %edx

		cmpl %edx, %eax
		jg no_swap
		je equal


equal:
  incq %r11
  subq $1, %r9
  jmp inner_loop

no_swap:
     movl %edx, (%rdi, %r10, 4)
     movl %eax, (%rdi, %r11, 4)
	 jmp equal

next_outer:
     movl (%rdi, %r10, 4), %edx
     movl (%rdi, %rcx, 4), %eax

     incq %r10
     movq $0, %r11
     movq %rsi, %r9
     subq $1, %r8
     jmp outer_loop

continue:

    movq %rsi, %rax
    movq $2, %rcx
    cdq
    idiv %rcx
    cmpq $0, %rdx
    jne is_odd
    je is_even

is_odd:
     movl (%rdi, %rax,4), %eax
     jmp end

is_even:
     subq $1, %rax
     movl (%rdi, %rax,4), %eax
     jmp end




end:
    ret
