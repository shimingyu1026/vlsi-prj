
CC = gcc
CXX = g++
PRJ = playground

TOPNAME = top
INC_PATH ?=

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
V_BUILD_DIR = ../verilogFile
BIN = $(BUILD_DIR)/$(TOPNAME)

IMG ?=

default: $(BIN)

$(shell mkdir -p $(BUILD_DIR))


VSRCS = $(shell find $(abspath ../verilogFile) -name "*.sv")
CSRCS = $(shell find $(abspath ./csrc) -name "*.c" -or -name "*.cpp" -or -name "*.cc")


verilog:
	mkdir -p $(V_BUILD_DIR)
	mill --mill-version 0.11.12 -i __.reformat 
	mill --mill-version 0.11.12 -i $(PRJ).runMain Elaborate --target-dir $(V_BUILD_DIR) --throw-on-first-error
	@echo "Verilog generated in $(V_BUILD_DIR)"

clean:
	-rm -rf $(BUILD_DIR)
	-rm -rf ./wave.vcd




.PHONY: test verilog help reformat checkformat clean bsp idea sim default all 

