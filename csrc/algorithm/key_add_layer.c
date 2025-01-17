#include "../include/common.h"
#include "../include/algorithm.h"

void AddRoundKey (uint8_t (*PlainArray)[4],uint8_t (*ExtendKeyArray)[44],uint32_t MinCol)
{
    printf("AddRoundKey:\n");
    for(int i = 0; i < 4; i++)
    {
        for(int j = 0; j < 4; j++)
        {
            PlainArray[i][j] = PlainArray[i][j] ^ ExtendKeyArray[i][j + MinCol * 4];
            printf("%02x ", ExtendKeyArray[i][j + MinCol * 4]);
        }
        printf("\n");
    }
    printf("\n");
}