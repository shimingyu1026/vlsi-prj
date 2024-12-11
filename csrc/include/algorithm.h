#ifndef __ALGORITHM_H__
#define __ALGORITHM_H__

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>
#include <stdint.h>
#include "common.h"
// 密钥加法
void AddRoundKey(uint8_t (*PlainArray)[4], uint8_t (*ExtendKeyArray)[44], uint32_t MinCol);

// 字节代换
void Plain_S_Substitution(uint8_t *PlainArray);
void Cipher_S_Substitution(uint8_t *CipherArray);

// 行移位
void ShiftRows(uint32_t *PlainArray);
void ReShiftRows(uint32_t *CipherArray);

// 列混淆
void MixColum(uint8_t (*PlainArray)[4]);
void ReMixColum(uint8_t (*PlainArray)[4]);
uint8_t GaloisMultiplication(uint8_t Num_L, uint8_t Num_R);

// 子密钥生成
void CalculateExtendKeyArray(const uint8_t (*PasswordArray)[4], uint8_t (*ExtendKeyArray)[44]);

// 加密
void AESEncode(const uint8_t (*PasswordArray)[4], uint8_t (*data)[4]);
// 解密
void AESDecode(const uint8_t (*PasswordArray)[4], uint8_t (*data)[4]);
#endif