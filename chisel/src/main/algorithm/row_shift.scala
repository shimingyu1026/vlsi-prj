import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class ShiftRow extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
  })
  for (i <- 0 until 4) {
    io.data_out(0)(i) := io.data_in(0)(i)
  }

  io.data_out(1)(0) := io.data_in(1)(1)
  io.data_out(1)(1) := io.data_in(1)(2)
  io.data_out(1)(2) := io.data_in(1)(3)
  io.data_out(1)(3) := io.data_in(1)(0)

  io.data_out(2)(0) := io.data_in(2)(2)
  io.data_out(2)(1) := io.data_in(2)(3)
  io.data_out(2)(2) := io.data_in(2)(0)
  io.data_out(2)(3) := io.data_in(2)(1)

  io.data_out(3)(0) := io.data_in(3)(3)
  io.data_out(3)(1) := io.data_in(3)(0)
  io.data_out(3)(2) := io.data_in(3)(1)
  io.data_out(3)(3) := io.data_in(3)(2)
}

class InvShiftRow extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
  })
  for (i <- 0 until 4) {
    io.data_out(0)(i) := io.data_in(0)(i)
  }

  io.data_out(1)(0) := io.data_in(1)(3)
  io.data_out(1)(1) := io.data_in(1)(0)
  io.data_out(1)(2) := io.data_in(1)(1)
  io.data_out(1)(3) := io.data_in(1)(2)

  io.data_out(2)(0) := io.data_in(2)(2)
  io.data_out(2)(1) := io.data_in(2)(3)
  io.data_out(2)(2) := io.data_in(2)(0)
  io.data_out(2)(3) := io.data_in(2)(1)

  io.data_out(3)(0) := io.data_in(3)(1)
  io.data_out(3)(1) := io.data_in(3)(2)
  io.data_out(3)(2) := io.data_in(3)(3)
  io.data_out(3)(3) := io.data_in(3)(0)
}
