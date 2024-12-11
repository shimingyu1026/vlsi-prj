import chisel3._
import chisel3.util._
import chisel3.experimental._

import common.AESConstants._

class top extends Module {
    val io = IO(new Bundle {
        val data_in = Input(Vec(4, Vec(4, UInt(8.W))))
        val data_out = Output(Vec(4, Vec(4, UInt(8.W))))
        //val key_in = Input(Vec(4, Vec(4, UInt(8.W))))
    })
    val dut = Module(new InvShiftRow)

    dut.io.data_in := io.data_in
    io.data_out   := dut.io.data_out


}

/* // 1. 将它定义为单例对象中的val常量
object AES_Constants {
  val MixColumnsMatrix = VecInit(...)  // 只会被初始化一次
}

// 2. 在顶层模块中定义，然后传递给子模块
class AES_Top extends Module {
  val mixMatrix = VecInit(...)  // 定义一次
  val subModule1 = Module(new SubModule1(mixMatrix))
  val subModule2 = Module(new SubModule2(mixMatrix))
}

// 3. 使用参数化方式传递
class SubModule(val matrix: Vec[Vec[UInt]]) extends Module {
  // 使用传入的matrix参数
} */