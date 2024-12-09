import numpy as np
from PIL import Image
import matplotlib.pyplot as plt

class JPEGEncoder:
    def __init__(self, image_array):
        """
        初始化JPEG编码器
        image_array: numpy数组，形状为(height, width, 3)的RGB图像
        """
        self.image_array = image_array
        
        # 标准量化表 - 亮度
        self.Y_quantization_table = np.array([
            [16, 11, 10, 16, 24, 40, 51, 61],
            [12, 12, 14, 19, 26, 58, 60, 55],
            [14, 13, 16, 24, 40, 57, 69, 56],
            [14, 17, 22, 29, 51, 87, 80, 62],
            [18, 22, 37, 56, 68, 109, 103, 77],
            [24, 35, 55, 64, 81, 104, 113, 92],
            [49, 64, 78, 87, 103, 121, 120, 101],
            [72, 92, 95, 98, 112, 100, 103, 99]
        ])
        
        # 标准量化表 - 色度
        self.CbCr_quantization_table = np.array([
            [17, 18, 24, 47, 99, 99, 99, 99],
            [18, 21, 26, 66, 99, 99, 99, 99],
            [24, 26, 56, 99, 99, 99, 99, 99],
            [47, 66, 99, 99, 99, 99, 99, 99],
            [99, 99, 99, 99, 99, 99, 99, 99],
            [99, 99, 99, 99, 99, 99, 99, 99],
            [99, 99, 99, 99, 99, 99, 99, 99],
            [99, 99, 99, 99, 99, 99, 99, 99]
        ])

    def rgb_to_ycbcr(self):
        """将RGB图像转换为YCbCr色彩空间"""
        rgb = self.image_array.astype(np.float32)
        
        # 转换矩阵
        transform = np.array([
            [0.299, 0.587, 0.114],
            [-0.1687, -0.3313, 0.5],
            [0.5, -0.4187, -0.0813]
        ])
        
        # 调整形状以便进行矩阵乘法
        reshaped_rgb = rgb.reshape(-1, 3).T
        ycbcr = np.dot(transform, reshaped_rgb)
        
        # 调整范围并重塑回原始形状
        ycbcr[0] += 0
        ycbcr[1:] += 128
        return ycbcr.T.reshape(rgb.shape)

    def chroma_subsampling(self, ycbcr):
        """执行4:2:0色度抽样"""
        height, width = ycbcr.shape[:2]
        
        Y = ycbcr[:,:,0]
        # 对Cb和Cr进行2x2平均池化
        Cb = ycbcr[::2, ::2, 1]
        Cr = ycbcr[::2, ::2, 2]
        
        return Y, Cb, Cr

    def pad_image(self, image):
        """将图像填充为8的倍数"""
        height, width = image.shape[:2]
        new_height = ((height + 7) // 8) * 8
        new_width = ((width + 7) // 8) * 8
        
        if len(image.shape) == 3:
            padded = np.zeros((new_height, new_width, image.shape[2]))
        else:
            padded = np.zeros((new_height, new_width))
        
        padded[:height, :width] = image
        return padded

    def split_into_blocks(self, channel):
        """将通道数据分割为8x8的块"""
        channel = self.pad_image(channel)
        blocks = []
        height, width = channel.shape
        
        for i in range(0, height, 8):
            for j in range(0, width, 8):
                block = channel[i:i+8, j:j+8].copy()
                blocks.append(block - 128)  # 中心化
        
        return blocks

    def dct2d(self, block):
        """执行2D DCT变换"""
        return np.round(np.fft.fft2(block) * np.sqrt(1/(8*8)))

    def quantize(self, dct_block, is_luminance=True):
        """量化DCT系数"""
        qtable = self.Y_quantization_table if is_luminance else self.CbCr_quantization_table
        return np.round(dct_block / qtable)

    def zigzag_scan(self, matrix):
        """Z字形扫描"""
        rows, cols = matrix.shape
        solution=[[] for i in range(rows + cols - 1)]
        
        for i in range(rows):
            for j in range(cols):
                sum = i + j
                if sum % 2 == 0:
                    solution[sum].insert(0, matrix[i][j])
                else:
                    solution[sum].append(matrix[i][j])
                    
        return [x for y in solution for x in y]

    def encode_image(self):
        """执行完整的JPEG编码过程"""
        print("开始编码...")
        # 1. RGB到YCbCr的转换
        print("正在进行颜色空间转换...")
        ycbcr = self.rgb_to_ycbcr()
        
        # 2. 色度抽样
        print("正在进行色度抽样...")
        Y, Cb, Cr = self.chroma_subsampling(ycbcr)
        
        # 3. 分块
        print("正在进行图像分块...")
        Y_blocks = self.split_into_blocks(Y)
        Cb_blocks = self.split_into_blocks(Cb)
        Cr_blocks = self.split_into_blocks(Cr)
        
        # 用于存储编码结果
        encoded = {'Y': [], 'Cb': [], 'Cr': []}
        
        # 4. 处理每个块
        print("正在处理图像块...")
        for channel_name, blocks in [('Y', Y_blocks), ('Cb', Cb_blocks), ('Cr', Cr_blocks)]:
            for block in blocks:
                # DCT变换
                dct_block = self.dct2d(block)
                # 量化
                quantized = self.quantize(dct_block, is_luminance=(channel_name=='Y'))
                # Z字形扫描
                zigzag = self.zigzag_scan(quantized)
                encoded[channel_name].append(zigzag)
        
        print("编码完成!")
        return encoded

def main():
    # 创建一个800x800的测试图像
    img = np.random.randint(0, 255, (800, 800, 3), dtype=np.uint8)
    
    # 创建编码器实例
    encoder = JPEGEncoder(img)
    
    # 执行编码
    encoded_data = encoder.encode_image()
    
    # 打印一些编码结果
    print("\n编码结果示例:")
    print("Y通道第一个块的前10个系数:", encoded_data['Y'][0][:10])
    print("Cb通道第一个块的前10个系数:", encoded_data['Cb'][0][:10])
    print("Cr通道第一个块的前10个系数:", encoded_data['Cr'][0][:10])

    # 可视化原始图像
    plt.figure(figsize=(10, 5))
    plt.subplot(121)
    plt.imshow(img)
    plt.title('原始图像')
    plt.axis('off')
    
    # 可视化Y通道的第一个8x8块
    plt.subplot(122)
    first_block = np.array(encoded_data['Y'][0]).reshape(8, 8)
    plt.imshow(first_block, cmap='gray')
    plt.title('Y通道第一个块的DCT系数')
    plt.axis('off')
    plt.show()

if __name__ == "__main__":
    main()