#include "../include/common.h"
#include "../include/algorithm.h"

void Key_S_Substitution(uint8_t (*ExtendKeyArray)[44], uint32_t nCol)
{

    for (int i = 0; i < 4; i++)
    {
        ExtendKeyArray[i][nCol] = S_Table[(ExtendKeyArray[i][nCol]) >> 4][(ExtendKeyArray[i][nCol]) & 0x0F];
        //printf("%x ", ExtendKeyArray[i][nCol]);
    }
    //printf("\n");
}

void G_Function(uint8_t (*ExtendKeyArray)[44], uint32_t nCol)
{

    // 1、将扩展密钥矩阵的nCol-1列复制到nCol列上，并将nCol列第一行的元素移动到最后一行，其他行数上移一行
    for (int i = 0; i < 4; i++)
    {
        ExtendKeyArray[i][nCol] = ExtendKeyArray[(i + 1) % 4][nCol - 1];
    }
    if (nCol == 4)
        //printf("bbb: %x%x%x%x\n", ExtendKeyArray[3][nCol], ExtendKeyArray[2][nCol], ExtendKeyArray[1][nCol], ExtendKeyArray[0][nCol]);

    // 2、将nCol列进行S盒替换
    Key_S_Substitution(ExtendKeyArray, nCol);
    if (nCol == 4)
    {
        //printf("aaa: %x%x%x%x\n", ExtendKeyArray[3][nCol], ExtendKeyArray[2][nCol], ExtendKeyArray[1][nCol], ExtendKeyArray[0][nCol]);
    }
    // 3、将该列第一行元素与Rcon进行异或运算
    ExtendKeyArray[0][nCol] ^= Rcon[nCol / 4];
}

void CalculateExtendKeyArray(const uint8_t (*PasswordArray)[4], uint8_t (*ExtendKeyArray)[44])
{
    // 1、将密钥数组放入前四列扩展密钥组
    for (int i = 0; i < 16; i++)
    {
        ExtendKeyArray[i & 0x03][i >> 2] = PasswordArray[i & 0x03][i >> 2];
    }

    // 2、计算扩展矩阵的后四十列
    for (int i = 1; i < 11; i++) // 进行十轮循环
    {
        //(1)如果列号是4的倍数，这执行G函数  否则将nCol-1列复制到nCol列上
        G_Function(ExtendKeyArray, 4 * i);

        //(2)每一轮中，各列进行异或运算
        //      列号是4的倍数
        for (int k = 0; k < 4; k++) // 行号
        {
            ExtendKeyArray[k][4 * i] = ExtendKeyArray[k][4 * i] ^ ExtendKeyArray[k][4 * (i - 1)];
        }

        //      其他三列
        for (int j = 1; j < 4; j++) // 每一轮的列号
        {
            for (int k = 0; k < 4; k++) // 行号
            {
                ExtendKeyArray[k][4 * i + j] = ExtendKeyArray[k][4 * i + j - 1] ^ ExtendKeyArray[k][4 * (i - 1) + j];
            }
        }
    }
}