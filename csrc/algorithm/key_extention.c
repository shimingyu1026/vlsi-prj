#include "../include/common.h"
#include "../include/algorithm.h"
uint8_t d0;
uint8_t d1;
uint8_t d2;
uint8_t d3;

void Key_S_Substitution(uint8_t (*ExtendKeyArray)[44], uint32_t nCol)
{

    for (int i = 0; i < 4; i++)
    {
        ExtendKeyArray[i][nCol] = S_Table[(ExtendKeyArray[i][nCol]) >> 4][(ExtendKeyArray[i][nCol]) & 0x0F];
        //printf("%x ", ExtendKeyArray[i][nCol]);
    }
    //printf("\n");
}

void G_Function(uint8_t *data, uint32_t nCol)
{
    for (int i = 0; i < 4; i++)
    {
        data[i] = S_Table[data[i] >> 4][data[i] & 0x0F];
    }
    data[2] = data[2] ^ Rcon[nCol];

    d0 = data[0];
    d1 = data[1];
    d2 = data[2];
    d3 = data[3];

    data[0] = d3;
    data[1] = d0;
    data[2] = d1;
    data[3] = d2;
}

void CalculateExtendKeyArray(const uint8_t (*PasswordArray)[4], uint8_t (*ExtendKeyArray)[44])
{
    // 1、将密钥数组放入前四列扩展密钥组
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            ExtendKeyArray[i][j] = PasswordArray[i][j];
        }
    }
    // 2、计算扩展矩阵的后四十列
    for (int i = 1; i < 11; i++) // 进行十轮循环
    {
        uint8_t g_out[4];
        uint8_t w3[4];
        uint8_t w2[4];
        uint8_t w1[4];
        uint8_t w0[4];
        for (int j = 0; j < 4; j++)
        {
            w0[j] = ExtendKeyArray[3 - j][4 * (i - 1)];
            w1[j] = ExtendKeyArray[3 - j][4 * (i - 1) + 1];
            w2[j] = ExtendKeyArray[3 - j][4 * (i - 1) + 2];
            w3[j] = ExtendKeyArray[3 - j][4 * (i - 1) + 3];
            g_out[j] = ExtendKeyArray[3 - j][4 * (i - 1) + 3];
        }
        G_Function(g_out, i);

        *(uint32_t *)w0 = *(uint32_t *)w0 ^ *(uint32_t *)g_out;
        *(uint32_t *)w1 = *(uint32_t *)w1 ^ *(uint32_t *)w0;
        *(uint32_t *)w2 = *(uint32_t *)w2 ^ *(uint32_t *)w1;
        *(uint32_t *)w3 = *(uint32_t *)w3 ^ *(uint32_t *)w2;

        for (int j = 0; j < 4; j++)
        {
            ExtendKeyArray[3 - j][4 * i] = w0[j];
            ExtendKeyArray[3 - j][4 * i + 1] = w1[j];
            ExtendKeyArray[3 - j][4 * i + 2] = w2[j];
            ExtendKeyArray[3 - j][4 * i + 3] = w3[j];
        }
    }
}