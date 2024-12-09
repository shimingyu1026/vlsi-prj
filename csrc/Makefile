CC = gcc
CXX = g++

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir

CSRCS = $(shell find $(abspath .) -name "*.c" -or -name "*.cpp" -or -name "*.cc")

all: $(CSRCS)
	@echo "Compiling C files..."
	@mkdir -p $(BUILD_DIR)
	@mkdir -p $(OBJ_DIR)
	@$(CC) -o $(OBJ_DIR)/main -I./include $(CSRCS)
	@echo "Compiling C files down"
	@./build/obj_dir/main
