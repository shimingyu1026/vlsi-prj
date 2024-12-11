CC = gcc
CXX = g++

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
V_BUILD_DIR = ../verilogFile

CSRCS = $(shell find $(abspath .) -name "*.c" -or -name "*.cpp" -or -name "*.cc")
VSRCS = $(shell find $(abspath ../verilogFile) -name "*.sv" -o -name "*.v")

TOPNAME = top
INC_PATH ?= ./include

VERILATOR = verilator
VERILATOR_CFLAGS += -MMD -cc -trace \
				-O3 --x-assign fast --x-initial fast --noassert  --build --timescale "1ns/1ns" --no-timing

BIN = $(BUILD_DIR)/$(TOPNAME)

default: $(BIN)

$(shell mkdir -p $(BUILD_DIR))
$(shell mkdir -p $(OBJ_DIR))


# rules for verilator
INCFLAGS = $(addprefix -I, $(INC_PATH))
CXXFLAGS += $(INCFLAGS) -DTOP_NAME="\"V$(TOPNAME)\"" 

$(BIN): $(VSRCS) $(CSRCS) 
	@rm -rf $(OBJ_DIR)
	@$(VERILATOR) $(VERILATOR_CFLAGS) \
		$(addprefix -CFLAGS , $(CXXFLAGS)) \
		--top-module $(TOPNAME) $^ \
		--Mdir $(OBJ_DIR) --exe -o $(abspath $(BIN))

sim: default 
	$(BIN) 


clean:
	-rm -rf $(BUILD_DIR)
	-rm -rf ./wave.vcd
