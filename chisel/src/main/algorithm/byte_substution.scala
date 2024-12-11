import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class ByteSub extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })
    val sbox = SBox

     for(i <- 0 until 4) {
        for(j <- 0 until 4) {
            io.data_out(i)(j) := sbox((io.data_in(i)(j) & "hF0".U) | (io.data_in(i)(j) & "hF".U))
        }
    }   
}

class InvByteSub extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })
    val invsbox = InvSBox

     for(i <- 0 until 4) {
        for(j <- 0 until 4) {
            io.data_out(i)(j) := invsbox((io.data_in(i)(j) & "hF0".U) | (io.data_in(i)(j) & "hF".U))
        }
    }

}