#include "common.h"
#include "algorithm.h"
int main(int argc, char *argv[])
{
    uint8_t PasswordArray[4][4] = {
        {0xaa, 0x09, 0x0c, 0x8d},
        {0x90, 0x0c, 0x64, 0x9d},
        {0xa0, 0xdd, 0x43, 0x4f},
        {0xf0, 0x98, 0xcc, 0xcc}};
    uint8_t ExtendKeyArray[4][44] = {};

    CalculateExtendKeyArray(PasswordArray, ExtendKeyArray);

    uint64_t data[2] = {0x0000cc0000aa0000, 0x00aa0000cc000000};
    printf("data: %016lx %016lx\n", data[0], data[1]);
    AddRoundKey(data, ExtendKeyArray, 0);

    for (int i = 1; i < 11; i++)
    {
        Plain_S_Substitution(data);
        ShiftRows(data);
        if (i != 10)
        {
            MixColum(data);
        }

        AddRoundKey(data, ExtendKeyArray, i);
    }

    printf("data: %016lx %016lx\n", data[0], data[1]);

    for (int i = 1; i < 11; i++)
    {
        AddRoundKey(data, ExtendKeyArray, 11 - i);
        if(i != 1)
        {
            ReMixColum(data);
        }
        ReShiftRows(data);
        Cipher_S_Substitution(data);
    }
    AddRoundKey(data, ExtendKeyArray, 0);
    printf("data: %016lx %016lx\n", data[0], data[1]);

    return 0;
}