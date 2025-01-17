import chisel3._
import chisel3.util._
import chisel3.experimental._
import common.AESConstants._

class top extends Module {
  val io = IO(new Bundle {
    val data_in  = Input(UInt(1.W))
    val data_out = Output(UInt(1.W))
    val key_in   = Input(UInt(1.W))
  })

  val aol1 = Module(new algo_col_mid)
  val aol2 = Module(new algo_col_mid)

  val key1 = Module(new KeyExpansion)
  val key2 = Module(new KeyExpansion)

  key1.io.nCol := 1.U

  key2.io.key_in := key1.io.key_out
  key2.io.nCol   := 2.U

  for (i <- 0 until 4) {
    for (j <- 0 until 4) {
      aol1.io.data_in(i)(j) := io.data_in
      key1.io.key_in(i)(j)  := io.key_in
    }
  }

  aol1.io.key_in := key1.io.key_out

  aol2.io.data_in := aol1.io.data_out
  aol2.io.key_in  := key2.io.key_out

  io.data_out := aol2.io.data_out(2)(1)

  dontTouch(aol1.io.data_out)

}
