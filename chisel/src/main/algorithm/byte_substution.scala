import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class ByteSub extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })

     for(i <- 0 until 4) {
        for(j <- 0 until 4) {
            io.data_out(i)(j) := SBox(io.data_in(i)(j)>>4)(io.data_in(i)(j) & "h0f".U)
        }
    }
            
}

class InvByteSub extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })

     for(i <- 0 until 4) {
        for(j <- 0 until 4) {
            io.data_out(i)(j) := InvSBox(io.data_in(i)(j)>>4)(io.data_in(i)(j) & "h0f".U)
        }
    }
  
}