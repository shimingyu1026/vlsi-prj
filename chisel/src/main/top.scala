import chisel3._
import chisel3.util._
import chisel3.experimental._
import common.AESConstants._

class top extends Module {
  val io = IO(new Bundle {
    //val data_in  = Input(Vec(4, Vec(4, UInt(8.W))))
    val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
    val key_in   = Input(Vec(4, Vec(4, UInt(8.W))))
    //val nCol = Input(UInt(4.W))
  })

  val nCol    = RegInit(0.U(4.W))
  val key_mid = RegInit(VecInit(Seq.fill(4)(VecInit(Seq.fill(4)(0.U(8.W))))))
  val key     = RegInit(VecInit(Seq.fill(4)(VecInit(Seq.fill(4)(0.U(8.W))))))

  val dut = Module(new KeyExpansion)

  key     := io.key_in
  nCol    := nCol + 1.U
  key_mid := dut.io.key_out

  dut.io.key_in := Mux(nCol === 1.U || nCol === 0.U, key, key_mid)
  dut.io.nCol   := nCol
  io.data_out   := dut.io.key_out
  //dut.io <> io
}
