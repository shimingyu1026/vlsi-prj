import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class ColumnMix extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
  })

  for (i <- 0 until 4) {
    for (j <- 0 until 4) {
      io.data_out(i)(j) := Mux(
        io.data_in(0)(j) === 0.U,
        0.U,
        Table((ArcTable(MixColumns(i)(0)) +& ArcTable(io.data_in(0)(j))) % 255.U)
      ) ^
        Mux(io.data_in(1)(j) === 0.U, 0.U, Table((ArcTable(MixColumns(i)(1)) +& ArcTable(io.data_in(1)(j))) % 255.U)) ^
        Mux(io.data_in(2)(j) === 0.U, 0.U, Table((ArcTable(MixColumns(i)(2)) +& ArcTable(io.data_in(2)(j))) % 255.U)) ^
        Mux(io.data_in(3)(j) === 0.U, 0.U, Table((ArcTable(MixColumns(i)(3)) +& ArcTable(io.data_in(3)(j))) % 255.U))
    }
  }
}

class InvColumnMix extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
  })

  val table     = Table
  val arc_table = ArcTable
  val mix_array = InvMixColumns

  for (i <- 0 until 4) {
    for (j <- 0 until 4) {
      io.data_out(i)(j) := Mux(
        io.data_in(0)(j) === 0.U,
        0.U,
        table((arc_table(mix_array(i)(0)) +& arc_table(io.data_in(0)(j))) % 255.U)
      ) ^
        Mux(io.data_in(1)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(1)) +& arc_table(io.data_in(1)(j))) % 255.U)) ^
        Mux(io.data_in(2)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(2)) +& arc_table(io.data_in(2)(j))) % 255.U)) ^
        Mux(io.data_in(3)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(3)) +& arc_table(io.data_in(3)(j))) % 255.U))
    }
  }

}
