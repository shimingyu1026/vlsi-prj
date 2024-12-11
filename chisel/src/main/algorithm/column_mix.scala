import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class GaloisMultiplication extends Module {
    val io = IO(new Bundle {
        val data1_in = Input(UInt(8.W))
        val data2_in = Input(UInt(8.W))
        val result_out = Output(UInt(8.W))
    })
    val table = Table
    val arc_table = ArcTable
    val index = (arc_table(io.data1_in) + arc_table(io.data2_in)) % 255.U
    io.result_out := table(index)
}

class ColumnMix extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })

    val table = Table
    val arc_table = ArcTable
    val mix_array = MixColumns

    for (i <- 0 until 4) {
        for (j <- 0 until 4) {
           io.data_out(i)(j) := Mux(io.data_in(0)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(0)) +& arc_table(io.data_in(0)(j))) % 255.U)) ^ 
                                Mux(io.data_in(1)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(1)) +& arc_table(io.data_in(1)(j))) % 255.U)) ^ 
                                Mux(io.data_in(2)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(2)) +& arc_table(io.data_in(2)(j))) % 255.U)) ^ 
                                Mux(io.data_in(3)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(3)) +& arc_table(io.data_in(3)(j))) % 255.U))
        }
    }
}

class InvColumnMix extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    })

    val table = Table
    val arc_table = ArcTable
    val mix_array = InvMixColumns

    for (i <- 0 until 4) {
        for (j <- 0 until 4) {
           io.data_out(i)(j) := Mux(io.data_in(0)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(0)) +& arc_table(io.data_in(0)(j))) % 255.U)) ^ 
                                Mux(io.data_in(1)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(1)) +& arc_table(io.data_in(1)(j))) % 255.U)) ^ 
                                Mux(io.data_in(2)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(2)) +& arc_table(io.data_in(2)(j))) % 255.U)) ^ 
                                Mux(io.data_in(3)(j) === 0.U, 0.U, table((arc_table(mix_array(i)(3)) +& arc_table(io.data_in(3)(j))) % 255.U))
        }
    }

}