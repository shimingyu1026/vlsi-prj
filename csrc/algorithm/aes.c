#include "../include/common.h"
#include "../include/algorithm.h"

void AESEncode(const uint8_t (*PasswordArray)[4], uint8_t (*data)[4])
{
    uint8_t ExtendKeyArray[4][44] = {};
    CalculateExtendKeyArray(PasswordArray, ExtendKeyArray);
    AddRoundKey(data, ExtendKeyArray, 0);

    for (int i = 1; i < 11; i++)
    {
        Plain_S_Substitution((uint8_t *)data);
        ShiftRows((uint32_t *)data);
        if (i != 10)
        {
            MixColum(data);
        }

        AddRoundKey(data, ExtendKeyArray, i);
    }
}
void AESEncoderound(const uint8_t (*PasswordArray)[4], uint8_t (*data)[4])
{
    uint8_t ExtendKeyArray[4][44] = {};
    CalculateExtendKeyArray(PasswordArray, ExtendKeyArray);
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 44; j++)
        {
            printf("%02x ", ExtendKeyArray[i][j]);
        }
        printf("\n");
    }
    printf("\n");
    for (int i = 0; i <= 1; i++)
    {
        Plain_S_Substitution((uint8_t *)data);
        ShiftRows((uint32_t *)data);
        MixColum(data);
        AddRoundKey(data, ExtendKeyArray, i);
    }
}

void AESDecode(const uint8_t (*PasswordArray)[4], uint8_t (*data)[4])
{
    uint8_t ExtendKeyArray[4][44] = {};
    CalculateExtendKeyArray(PasswordArray, ExtendKeyArray);

    for (int i = 1; i < 11; i++)
    {
        AddRoundKey(data, ExtendKeyArray, 11 - i);
        if (i != 1)
        {
            ReMixColum(data);
        }
        ReShiftRows((uint32_t *)data);
        Cipher_S_Substitution((uint8_t*)data);
    }

    AddRoundKey(data, ExtendKeyArray, 0);
}