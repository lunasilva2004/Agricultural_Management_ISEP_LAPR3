#include <stdio.h>
#include "asm.h"

int main() {
    char input[] = "\\sensor_id:8#type:atmospheric_temperature#value:21.60#unit:celsius#time:2470030";	
    char token[] = "value";
    int output;

    extract_token(input, token, &output);
    
    if (output == 0) {
        printf("Token not found or value is not a valid integer.\n");
    } else {
        printf("Extracted value: %d\n", output);
    }

    return 0;
}
