.section .text
    .global move_num_vec

move_num_vec:

    # Check if there are enough elements in the array
    cmpl %esi, %r8d
    jg not_enough_elements

    # Initialize read and write pointers
    movl (%rdx), %r10d
    movl (%rcx), %r11d

    # Check if read and write pointers are within array bounds
    cmpl %esi, %r10d
	jg not_enough_elements

	cmpl %esi, %r11d
	jg not_enough_elements

check:

	cmpl %r11d, %r10d
    je not_enough_elements

    # Check if read and write pointers are equal
    cmpl %r11d, %r10d
    je increment_read

    movl %r10d, %eax
    subl %r11d, %eax

    cmp %eax, %r8d
    jl not_enough_elements

    # Set up a copy loop
    mov %r8d, %ecx

copy_loop:
    # Restart points if the indices go beyond the array bounds
    cmpl %esi, %r10d
    jge reset_read

    cmpl %esi, %r11d
    jge reset_write

    # Copy an element from the source array to the destination vector
    pushq %rdx
    leaq (%rdi, %r10, 4), %rdx
    movl (%rdx), %eax
	movl %eax, (%r9)
	popq %rdx
	incq (%rdx)

    # Increment indices and update pointers
    incl %r10d
    addq $4, %r9
    decl %ecx
    jnz copy_loop

    # Set the return value to 1 and exit
    mov $1, %eax
    jmp end

reset_read:
	# Restart the read index
    movl $0, %r10d
    movl $0, (%rdx)
    jmp copy_loop

reset_write:
    # Restart the write index
    movl $0, %r11d
    jmp copy_loop

increment_read:
    # Increment the read index and continue
    incl %r10d
    jmp check

not_enough_elements:
    # Set the return value to 0 if there are not enough elements
    mov $0, %eax

end:

    # movq %r10, (%rdx)

    ret
