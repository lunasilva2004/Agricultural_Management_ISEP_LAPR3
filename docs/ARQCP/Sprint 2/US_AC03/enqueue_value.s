.section .note.GNU-stack,"",@progbits	#removes the warning " missing .note.GNU-stack section"


.section .text
	.global enqueue_value           # defines function 'enqueue_value' as global
	# parameters by registers:
	# int* array in rdi
	# int length in esi
	# int* read in rdx
	# int* write in rcx
	# int value in r8d

enqueue_value:	                    #function 'enqueue_value'

	# prologue
    pushq %rbp
    movq %rsp, %rbp

    movl (%rcx), %r9d               # Copies the value in rcx and places it in r9 (write)
    cmp %esi, %r9d                  # Compares length(esi) with write(r9)
    jl write_value                  # If write < length, continue with writing

    xor %r9d, %r9d                  # Reset write to 0, in order to overwrite the 1st position of the array

write_value:
    mov %r9d, %eax                  # Copies the value of %r9 to %rax (for subsequent calculations) - write

    lea (%rdi, %rax, 4), %rdx       # Calculate the memory address to write the value
    mov %r8d, (%rdx)                # Write the value to the array

    inc %r9d                        # Increment the write pointer
    cmp %r9d, %esi                  # Compare with the length of the array
    jne update_write                # If write < length, continue with writing

    xor %r9d, %r9d                  # Reset write to 0 if it reaches the end of the buffer

update_write:
    mov %r9d, (%rcx)                # Update the value of the write pointer in memory
    jmp end                         # Jump to the end of the function

end:

	# epilogue
    mov %rbp, %rsp
    pop %rbp

    ret
