#ifndef __COMMON_H__
#define __COMMON_H__

#include <stdio.h>
#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>

// S盒
extern const uint8_t S_Table[16][16];
// 逆S盒
extern const uint8_t ReS_Table[16][16];

// 列混淆左乘矩阵
extern const uint8_t MixArray[4][4];

// 逆向列混淆矩阵
extern const uint8_t ReMixArray[4][4];
// 用于密钥扩展    Rcon[0]作为填充，没有实际用途
extern const uint32_t Rcon[11];

#endif