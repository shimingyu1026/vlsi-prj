import chisel3._
import chisel3.util._
import chisel3.experimental._

class top extends Module {
    val io = IO(new Bundle {
        val a = Input(UInt(32.W))
        val b = Input(UInt(32.W))
        val c = Output(UInt(32.W))
    })
    io.c := io.a * io.b
}