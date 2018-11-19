# coding=utf-8
import cv2


videoCaputer = cv2.VideoCapture(0)
size = (int(videoCaputer.get(cv2.CAP_PROP_FRAME_HEIGHT)), int(videoCaputer.get(cv2.CAP_PROP_FRAME_WIDTH)))
print(size)  # 错误结果(480,640)

# 只要set下，貌似size就发生了变化
videoCaputer.set(cv2.CAP_PROP_FRAME_WIDTH, 5000)
videoCaputer.set(cv2.CAP_PROP_FRAME_HEIGHT, 5000)

size = (int(videoCaputer.get(cv2.CAP_PROP_FRAME_HEIGHT)), int(videoCaputer.get(cv2.CAP_PROP_FRAME_WIDTH)))
_, frame = videoCaputer.read()
print(size)  # 正确结果（1024,1280）
print(frame.shape)  # 正确结果（1024,1280, 3）
