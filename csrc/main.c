#include "Vtop.h"
#include "verilated.h"
#include "verilated_vcd_c.h"
#include "./include/common.h"
#include "./include/algorithm.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdint.h>

VerilatedContext *contextp = NULL;
Vtop *top = NULL;
VerilatedVcdC *tfp = NULL;

void sim_init(int argc, char **argv);
void reset(int n, Vtop *top);
void single_cycle(Vtop *top);
void init_random_data(uint8_t data[4][4]);
void print_data(uint8_t data[4][4]);
int main(int argc, char *argv[])
{
    // 设置随机种子
    srand(time(NULL));
    sim_init(argc, argv);
    reset(10, top);
    uint8_t data[4][4] = {};
    uint8_t key[4][4] = {};
    uint8_t extend_key[4][44] = {};

    uint8_t *data_in[4][4] = {
        {&top->io_data_in_0_0, &top->io_data_in_0_1, &top->io_data_in_0_2, &top->io_data_in_0_3},
        {&top->io_data_in_1_0, &top->io_data_in_1_1, &top->io_data_in_1_2, &top->io_data_in_1_3},
        {&top->io_data_in_2_0, &top->io_data_in_2_1, &top->io_data_in_2_2, &top->io_data_in_2_3},
        {&top->io_data_in_3_0, &top->io_data_in_3_1, &top->io_data_in_3_2, &top->io_data_in_3_3}};
    uint8_t *data_out[4][4] = {
        {&top->io_data_out_0_0, &top->io_data_out_0_1, &top->io_data_out_0_2, &top->io_data_out_0_3},
        {&top->io_data_out_1_0, &top->io_data_out_1_1, &top->io_data_out_1_2, &top->io_data_out_1_3},
        {&top->io_data_out_2_0, &top->io_data_out_2_1, &top->io_data_out_2_2, &top->io_data_out_2_3},
        {&top->io_data_out_3_0, &top->io_data_out_3_1, &top->io_data_out_3_2, &top->io_data_out_3_3}};
    /*
    uint8_t *key_in[4][4] = {
        {&top->io_key_in_0_0, &top->io_key_in_0_1, &top->io_key_in_0_2, &top->io_key_in_0_3},
        {&top->io_key_in_1_0, &top->io_key_in_1_1, &top->io_key_in_1_2, &top->io_key_in_1_3},
        {&top->io_key_in_2_0, &top->io_key_in_2_1, &top->io_key_in_2_2, &top->io_key_in_2_3},
        {&top->io_key_in_3_0, &top->io_key_in_3_1, &top->io_key_in_3_2, &top->io_key_in_3_3}};
*/
    init_random_data(data);
    init_random_data(key);

    print_data(data);
    print_data(key);

    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            *data_in[i][j] = data[i][j];
            //*key_in[i][j] = key[i][j];
        }
    }
    top->eval();

    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            printf("%02x", *data_out[i][j]);
        }
    }

    printf("\n");

    CalculateExtendKeyArray(key, extend_key);
    ReShiftRows((uint32_t *)data);

    print_data(data);

    return 0;
}

void print_data(uint8_t data[4][4])
{
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            printf("%02x", data[i][j]);
        }
    }
    printf("\n");
}

void sim_init(int argc, char **argv)
{
    Verilated::commandArgs(argc, argv);
    contextp = new VerilatedContext;
    contextp->commandArgs(argc, argv);
    top = new Vtop{contextp};
}

void single_cycle(Vtop *top)
{
    top->clock = 0;
    top->eval();
    top->clock = 1;
    top->eval();
}
void reset(int n, Vtop *top)
{

    top->reset = 0;
    while (n-- > 0)
        single_cycle(top);
    top->reset = 1;
}

// 初始化随机数据
void init_random_data(uint8_t data[4][4])
{

    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            // 生成 0x00 到 0xFF 之间的随机数
            data[i][j] = (uint8_t)(rand() & 0xFF);
        }
    }
}