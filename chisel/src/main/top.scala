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

  val a1 = Module(new KeyExpansion)
  val a2 = Module(new KeyExpansion)

  a1.io.key_in := io.key_in
  a1.io.nCol   := 1.U
  a2.io.key_in := a1.io.key_out
  a2.io.nCol   := 2.U
  io.data_out  := a1.io.key_out

  /*
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
  printf("nCol: %d\n", nCol)
  printf("key_in: \n")
  for (i <- 0 until 4) {
    printf("  %d: ", i.U)
    for (j <- 0 until 4) {
      printf("%x ", dut.io.key_in(i)(j))
    }
    printf("\n")
  }
  //dut.io <> io*/
}
