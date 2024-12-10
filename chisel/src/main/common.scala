package common

import chisel3._
import chisel3.util._



object AESConstants {
  // S-box
  val SBox = VecInit(Seq(
    "h63".U(8.W), "h7c".U(8.W), "h77".U(8.W), "h7b".U(8.W), "hf2".U(8.W), "h6b".U(8.W), "h6f".U(8.W), "hc5".U(8.W), 
    "h30".U(8.W), "h01".U(8.W), "h67".U(8.W), "h2b".U(8.W), "hfe".U(8.W), "hd7".U(8.W), "hab".U(8.W), "h76".U(8.W),
    "hca".U(8.W), "h82".U(8.W), "hc9".U(8.W), "h7d".U(8.W), "hfa".U(8.W), "h59".U(8.W), "h47".U(8.W), "hf0".U(8.W), 
    "had".U(8.W), "hd4".U(8.W), "ha2".U(8.W), "haf".U(8.W), "h9c".U(8.W), "ha4".U(8.W), "h72".U(8.W), "hc0".U(8.W),
    "hb7".U(8.W), "hfd".U(8.W), "h93".U(8.W), "h26".U(8.W), "h36".U(8.W), "h3f".U(8.W), "hf7".U(8.W), "hcc".U(8.W), 
    "h34".U(8.W), "ha5".U(8.W), "he5".U(8.W), "hf1".U(8.W), "h71".U(8.W), "hd8".U(8.W), "h31".U(8.W), "h15".U(8.W),
    "h04".U(8.W), "hc7".U(8.W), "h23".U(8.W), "hc3".U(8.W), "h18".U(8.W), "h96".U(8.W), "h05".U(8.W), "h9a".U(8.W), 
    "h07".U(8.W), "h12".U(8.W), "h80".U(8.W), "he2".U(8.W), "heb".U(8.W), "h27".U(8.W), "hb2".U(8.W), "h75".U(8.W),
    "h09".U(8.W), "h83".U(8.W), "h2c".U(8.W), "h1a".U(8.W), "h1b".U(8.W), "h6e".U(8.W), "h5a".U(8.W), "ha0".U(8.W), 
    "h52".U(8.W), "h3b".U(8.W), "hd6".U(8.W), "hb3".U(8.W), "h29".U(8.W), "he3".U(8.W), "h2f".U(8.W), "h84".U(8.W),
    "h53".U(8.W), "hd1".U(8.W), "h00".U(8.W), "hed".U(8.W), "h20".U(8.W), "hfc".U(8.W), "hb1".U(8.W), "h5b".U(8.W), 
    "h6a".U(8.W), "hcb".U(8.W), "hbe".U(8.W), "h39".U(8.W), "h4a".U(8.W), "h4c".U(8.W), "h58".U(8.W), "hcf".U(8.W),
    "hd0".U(8.W), "hef".U(8.W), "haa".U(8.W), "hfb".U(8.W), "h43".U(8.W), "h4d".U(8.W), "h33".U(8.W), "h85".U(8.W), 
    "h45".U(8.W), "hf9".U(8.W), "h02".U(8.W), "h7f".U(8.W), "h50".U(8.W), "h3c".U(8.W), "h9f".U(8.W), "ha8".U(8.W),
    "h51".U(8.W), "ha3".U(8.W), "h40".U(8.W), "h8f".U(8.W), "h92".U(8.W), "h9d".U(8.W), "h38".U(8.W), "hf5".U(8.W), 
    "hbc".U(8.W), "hb6".U(8.W), "hda".U(8.W), "h21".U(8.W), "h10".U(8.W), "hff".U(8.W), "hf3".U(8.W), "hd2".U(8.W),
    "hcd".U(8.W), "h0c".U(8.W), "h13".U(8.W), "hec".U(8.W), "h5f".U(8.W), "h97".U(8.W), "h44".U(8.W), "h17".U(8.W), 
    "hc4".U(8.W), "ha7".U(8.W), "h7e".U(8.W), "h3d".U(8.W), "h64".U(8.W), "h5d".U(8.W), "h19".U(8.W), "h73".U(8.W),
    "h60".U(8.W), "h81".U(8.W), "h4f".U(8.W), "hdc".U(8.W), "h22".U(8.W), "h2a".U(8.W), "h90".U(8.W), "h88".U(8.W), 
    "h46".U(8.W), "hee".U(8.W), "hb8".U(8.W), "h14".U(8.W), "hde".U(8.W), "h5e".U(8.W), "h0b".U(8.W), "hdb".U(8.W),
    "he0".U(8.W), "h32".U(8.W), "h3a".U(8.W), "h0a".U(8.W), "h49".U(8.W), "h06".U(8.W), "h24".U(8.W), "h5c".U(8.W), 
    "hc2".U(8.W), "hd3".U(8.W), "hac".U(8.W), "h62".U(8.W), "h91".U(8.W), "h95".U(8.W), "he4".U(8.W), "h79".U(8.W),
    "he7".U(8.W), "hc8".U(8.W), "h37".U(8.W), "h6d".U(8.W), "h8d".U(8.W), "hd5".U(8.W), "h4e".U(8.W), "ha9".U(8.W), 
    "h6c".U(8.W), "h56".U(8.W), "hf4".U(8.W), "hea".U(8.W), "h65".U(8.W), "h7a".U(8.W), "hae".U(8.W), "h08".U(8.W),
    "hba".U(8.W), "h78".U(8.W), "h25".U(8.W), "h2e".U(8.W), "h1c".U(8.W), "ha6".U(8.W), "hb4".U(8.W), "hc6".U(8.W), 
    "he8".U(8.W), "hdd".U(8.W), "h74".U(8.W), "h1f".U(8.W), "h4b".U(8.W), "hbd".U(8.W), "h8b".U(8.W), "h8a".U(8.W),
    "h70".U(8.W), "h3e".U(8.W), "hb5".U(8.W), "h66".U(8.W), "h48".U(8.W), "h03".U(8.W), "hf6".U(8.W), "h0e".U(8.W), 
    "h61".U(8.W), "h35".U(8.W), "h57".U(8.W), "hb9".U(8.W), "h86".U(8.W), "hc1".U(8.W), "h1d".U(8.W), "h9e".U(8.W),
    "he1".U(8.W), "hf8".U(8.W), "h98".U(8.W), "h11".U(8.W), "h69".U(8.W), "hd9".U(8.W), "h8e".U(8.W), "h94".U(8.W), 
    "h9b".U(8.W), "h1e".U(8.W), "h87".U(8.W), "he9".U(8.W), "hce".U(8.W), "h55".U(8.W), "h28".U(8.W), "hdf".U(8.W),
    "h8c".U(8.W), "ha1".U(8.W), "h89".U(8.W), "h0d".U(8.W), "hbf".U(8.W), "he6".U(8.W), "h42".U(8.W), "h68".U(8.W), 
    "h41".U(8.W), "h99".U(8.W), "h2d".U(8.W), "h0f".U(8.W), "hb0".U(8.W), "h54".U(8.W), "hbb".U(8.W), "h16".U(8.W)
  ))

  val InvSBox = VecInit(Seq(
    "h52".U(8.W), "h09".U(8.W), "h6a".U(8.W), "hd5".U(8.W), "h30".U(8.W), "h36".U(8.W), "ha5".U(8.W), "h38".U(8.W), "hbf".U(8.W), "h40".U(8.W), "ha3".U(8.W), "h9e".U(8.W), "h81".U(8.W), "hf3".U(8.W), "hd7".U(8.W), "hfb".U(8.W),
    "h7c".U(8.W), "he3".U(8.W), "h39".U(8.W), "h82".U(8.W), "h9b".U(8.W), "h2f".U(8.W), "hff".U(8.W), "h87".U(8.W), "h34".U(8.W), "h8e".U(8.W), "h43".U(8.W), "h44".U(8.W), "hc4".U(8.W), "hde".U(8.W), "he9".U(8.W), "hcb".U(8.W),
    "h54".U(8.W), "h7b".U(8.W), "h94".U(8.W), "h32".U(8.W), "ha6".U(8.W), "hc2".U(8.W), "h23".U(8.W), "h3d".U(8.W), "hee".U(8.W), "h4c".U(8.W), "h95".U(8.W), "h0b".U(8.W), "h42".U(8.W), "hfa".U(8.W), "hc3".U(8.W), "h4e".U(8.W),
    "h08".U(8.W), "h2e".U(8.W), "ha1".U(8.W), "h66".U(8.W), "h28".U(8.W), "hd9".U(8.W), "h24".U(8.W), "hb2".U(8.W), "h76".U(8.W), "h5b".U(8.W), "ha2".U(8.W), "h49".U(8.W), "h6d".U(8.W), "h8b".U(8.W), "hd1".U(8.W), "h25".U(8.W),
    "h72".U(8.W), "hf8".U(8.W), "hf6".U(8.W), "h64".U(8.W), "h86".U(8.W), "h68".U(8.W), "h98".U(8.W), "h16".U(8.W), "hd4".U(8.W), "ha4".U(8.W), "h5c".U(8.W), "hcc".U(8.W), "h5d".U(8.W), "h65".U(8.W), "hb6".U(8.W), "h92".U(8.W),
    "h6c".U(8.W), "h70".U(8.W), "h48".U(8.W), "h50".U(8.W), "hfd".U(8.W), "hed".U(8.W), "hb9".U(8.W), "hda".U(8.W), "h5e".U(8.W), "h15".U(8.W), "h46".U(8.W), "h57".U(8.W), "ha7".U(8.W), "h8d".U(8.W), "h9d".U(8.W), "h84".U(8.W),
    "h90".U(8.W), "hd8".U(8.W), "hab".U(8.W), "h00".U(8.W), "h8c".U(8.W), "hbc".U(8.W), "hd3".U(8.W), "h0a".U(8.W), "hf7".U(8.W), "he4".U(8.W), "h58".U(8.W), "h05".U(8.W), "hb8".U(8.W), "hb3".U(8.W), "h45".U(8.W), "h06".U(8.W),
    "hd0".U(8.W), "h2c".U(8.W), "h1e".U(8.W), "h8f".U(8.W), "hca".U(8.W), "h3f".U(8.W), "h0f".U(8.W), "h02".U(8.W), "hc1".U(8.W), "haf".U(8.W), "hbd".U(8.W), "h03".U(8.W), "h01".U(8.W), "h13".U(8.W), "h8a".U(8.W), "h6b".U(8.W),
    "h3a".U(8.W), "h91".U(8.W), "h11".U(8.W), "h41".U(8.W), "h4f".U(8.W), "h67".U(8.W), "hdc".U(8.W), "hea".U(8.W), "h97".U(8.W), "hf2".U(8.W), "hcf".U(8.W), "hce".U(8.W), "hf0".U(8.W), "hb4".U(8.W), "he6".U(8.W), "h73".U(8.W),
    "h96".U(8.W), "hac".U(8.W), "h74".U(8.W), "h22".U(8.W), "he7".U(8.W), "had".U(8.W), "h35".U(8.W), "h85".U(8.W), "he2".U(8.W), "hf9".U(8.W), "h37".U(8.W), "he8".U(8.W), "h1c".U(8.W), "h75".U(8.W), "hdf".U(8.W), "h6e".U(8.W),
    "h47".U(8.W), "hf1".U(8.W), "h1a".U(8.W), "h71".U(8.W), "h1d".U(8.W), "h29".U(8.W), "hc5".U(8.W), "h89".U(8.W), "h6f".U(8.W), "hb7".U(8.W), "h62".U(8.W), "h0e".U(8.W), "haa".U(8.W), "h18".U(8.W), "hbe".U(8.W), "h1b".U(8.W),
    "hfc".U(8.W), "h56".U(8.W), "h3e".U(8.W), "h4b".U(8.W), "hc6".U(8.W), "hd2".U(8.W), "h79".U(8.W), "h20".U(8.W), "h9a".U(8.W), "hdb".U(8.W), "hc0".U(8.W), "hfe".U(8.W), "h78".U(8.W), "hcd".U(8.W), "h5a".U(8.W), "hf4".U(8.W),
    "h1f".U(8.W), "hdd".U(8.W), "ha8".U(8.W), "h33".U(8.W), "h88".U(8.W), "h07".U(8.W), "hc7".U(8.W), "h31".U(8.W), "hb1".U(8.W), "h12".U(8.W), "h10".U(8.W), "h59".U(8.W), "h27".U(8.W), "h80".U(8.W), "hec".U(8.W), "h5f".U(8.W),
    "h60".U(8.W), "h51".U(8.W), "h7f".U(8.W), "ha9".U(8.W), "h19".U(8.W), "hb5".U(8.W), "h4a".U(8.W), "h0d".U(8.W), "h2d".U(8.W), "he5".U(8.W), "h7a".U(8.W), "h9f".U(8.W), "h93".U(8.W), "hc9".U(8.W), "h9c".U(8.W), "hef".U(8.W),
    "ha0".U(8.W), "he0".U(8.W), "h3b".U(8.W), "h4d".U(8.W), "hae".U(8.W), "h2a".U(8.W), "hf5".U(8.W), "hb0".U(8.W), "hc8".U(8.W), "heb".U(8.W), "hbb".U(8.W), "h3c".U(8.W), "h83".U(8.W), "h53".U(8.W), "h99".U(8.W), "h61".U(8.W),
    "h17".U(8.W), "h2b".U(8.W), "h04".U(8.W), "h7e".U(8.W), "hba".U(8.W), "h77".U(8.W), "hd6".U(8.W), "h26".U(8.W), "he1".U(8.W), "h69".U(8.W), "h14".U(8.W), "h63".U(8.W), "h55".U(8.W), "h21".U(8.W), "h0c".U(8.W), "h7d".U(8.W)
  ))

  // MixColumns Matrix
  val MixColumns = VecInit(Seq(
    VecInit(Seq("h02".U(8.W), "h03".U(8.W), "h01".U(8.W), "h01".U(8.W))),
    VecInit(Seq("h01".U(8.W), "h02".U(8.W), "h03".U(8.W), "h01".U(8.W))),
    VecInit(Seq("h01".U(8.W), "h01".U(8.W), "h02".U(8.W), "h03".U(8.W))),
    VecInit(Seq("h03".U(8.W), "h01".U(8.W), "h01".U(8.W), "h02".U(8.W)))
  ))

  // Inverse MixColumns Matrix
  val InvMixColumns = VecInit(Seq(
    VecInit(Seq("h0e".U(8.W), "h0b".U(8.W), "h0d".U(8.W), "h09".U(8.W))),
    VecInit(Seq("h09".U(8.W), "h0e".U(8.W), "h0b".U(8.W), "h0d".U(8.W))),
    VecInit(Seq("h0d".U(8.W), "h09".U(8.W), "h0e".U(8.W), "h0b".U(8.W))),
    VecInit(Seq("h0b".U(8.W), "h0d".U(8.W), "h09".U(8.W), "h0e".U(8.W)))
  ))

  // Rcon Array for Key Expansion (Rcon[0] is padding)
  val Rcon = VecInit(Seq(
    "h00".U(32.W), // padding
    "h01".U(32.W),
    "h02".U(32.W),
    "h04".U(32.W),
    "h08".U(32.W),
    "h10".U(32.W),
    "h20".U(32.W),
    "h40".U(32.W),
    "h80".U(32.W),
    "h1b".U(32.W),
    "h36".U(32.W)
  ))

}