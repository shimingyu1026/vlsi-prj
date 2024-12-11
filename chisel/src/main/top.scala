import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class top extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
        //val key_in = Input(Vec(4, Vec(4, UInt(8.W))))
    })
    val dut = Module(new ColumnMix)
    dut.io.data_in := io.data_in
    io.data_out   := dut.io.data_out

}
