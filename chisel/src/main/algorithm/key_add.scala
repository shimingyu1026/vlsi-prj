import chisel3._
import chisel3.util._
import chisel3.experimental._
import chisel3.util.experimental._

import common.AESConstants._

class KeyAddr extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    val key_in   = Input(Vec(4, Vec(4, UInt(8.W))))
  })

  for (i <- 0 until 4) {
    for (j <- 0 until 4) {
      io.data_out(i)(j) := io.data_in(i)(j) ^ io.key_in(i)(j)
    }
  }
}
