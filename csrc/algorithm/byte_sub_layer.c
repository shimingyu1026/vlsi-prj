#include "common.h"
#include "algorithm.h"

// 字节代换
void Plain_S_Substitution(uint8_t *PlainArray)
{
    int ret = 0;

    for (int i = 0; i < 16; i++)
    {
        PlainArray[i] = S_Table[PlainArray[i] >> 4][PlainArray[i] & 0x0F];
    }
}

// 逆字节代换
void Cipher_S_Substitution(uint8_t *CipherArray)
{

    for (int i = 0; i < 16; i++)
    {
        CipherArray[i] = ReS_Table[CipherArray[i] >> 4][CipherArray[i] & 0x0F];
    }
}
