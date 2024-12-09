# Python示例：使用加密安全的随机数生成器
import secrets

# 使用secrets模块
key = secrets.token_bytes(16)
print(type(key))