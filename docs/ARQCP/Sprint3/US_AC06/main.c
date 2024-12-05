#include <stdio.h>
#include "asm.h"

int main() {
	
    char input[] = "\\sensor_id:8#type:atmospheric_temperature#value:21.60#unit:celsius#time:2470030";
    char token[] = "value";
    int output;

    int funcStatus = extract_token(input, token, &output);
    
    if (funcStatus == 0) {
        printf("\nToken -> %s < not found or value obtained is not a valid integer.\n", token);
    } else {
		printf("\nToken -> %s <- found successfully! \nExtracted value: %d\n", token, output);
    }

    return 0;
}
