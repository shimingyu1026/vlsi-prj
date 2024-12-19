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
        g_out[0] = ExtendKeyArray[3][(i) * 4 - 1];
        g_out[1] = ExtendKeyArray[2][(i) * 4 - 1];
        g_out[2] = ExtendKeyArray[1][(i) * 4 - 1];
        g_out[3] = ExtendKeyArray[0][(i) * 4 - 1];

        G_Function(g_out, i);
    }
}