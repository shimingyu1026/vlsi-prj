#include "common.h"
#include "algorithm.h"

void ShiftRows(uint32_t *PlainArray)
{

    // 第一行 不移位
    // PlainArray[0] = PlainArray[0];

    // 第二行 左移8Bit
    PlainArray[1] = (PlainArray[1] << 8) | (PlainArray[1] >> 24);

    // 第三行 左移16Bit
    PlainArray[2] = (PlainArray[2] << 16) | (PlainArray[2] >> 16);

    // 第四行 左移24Bit
    PlainArray[3] = (PlainArray[3] << 24) | (PlainArray[3] >> 8);
}

void ReShiftRows(uint32_t *CipherArray)
{

    // 第一行 不移位
    // CipherArray[0] = CipherArray[0];

    // 第二行 右移8Bit
    CipherArray[1] = (CipherArray[1] >> 8) | (CipherArray[1] << 24);

    // 第三行 右移16Bit
    CipherArray[2] = (CipherArray[2] >> 16) | (CipherArray[2] << 16);

    // 第四行 右移24Bit
    CipherArray[3] = (CipherArray[3] >> 24) | (CipherArray[3] << 8);
}
