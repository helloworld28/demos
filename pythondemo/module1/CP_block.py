#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import threading
import time
from multiprocessing import Process
import asyncore, socket
import os
import cv2
import numpy as np

import imageio
from utils.app_utils import FPS, WebcamVideoStream, draw_boxes_and_labels, depth_LUT, k_LUT, b_LUT, cal_area_center
from option import Options
import datetime
import Queue
import traceback
import binascii
import math

image_height = 0
image_width = 0
max_width = 0
img_count = 0
IS_DEBUG = True


def printHex(content):
    return binascii.hexlify(bytearray(content))

    # return ':'.join("{:02x}".format(ord(str(c))) for c in content)


class boss():
    def __init__(self):
        data = list_manage()
        lock = threading.Lock()
        self.data = data
        self.client = HTTPClient('192.168.1.251', 5000)
        c0 = consume(data, 'c0', lock, self.client)
        p = product(data, 'p', lock)
        # loop_thread = threading.Thread(target=loop, name="Asyncore Loop", args=(self.client, ))
        # # If you want to make the thread a daemon
        # # loop_thread.daemon = True
        # loop_thread.start()
        c0.start()
        p.start()
        self.go()

    def go(self):
        while True:
            time.sleep(100)
            # # print('root process',self.data)
            # self.data.# print()
            # print('boss check , ',self.data.show())


class HTTPClient(asyncore.dispatcher):
    def __init__(self, host, port):
        asyncore.dispatcher.__init__(self)
        print ' w ', image_width, ' h ', image_height

        self.host = host
        self.port = port
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connect((host, port))
        self.buffer = Queue.Queue()
        self.receive_buffer = Queue.Queue()

        # self.buffer = 'GET %s HTTP/1.0\r\n\r\n' % "a"

    def initate_connection_with_server(self):
        print("trying to initialize connection with server...")
        asyncore.dispatcher.__init__(self)
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connect((self.host, self.port))

    def handle_connect(self):
        print('connected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!')
        # sys.exit("connected")

    def handle_error(self):
        print "error!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        self.initate_connection_with_server()

    def handle_close(self):
        self.close()

    def back_save(self, s, index, value, w):
        for i in range(7):
            try:
                # print str(w) + 'index=', index, ' value=', value, ' i=', i, ' w=', w

                s[index / w, index * 7 % (w * 7) + i, :] = ([0, 255, 0] if ((value >> i) % 2 == 1) else [0, 0, 0])
            except Exception as e:
                print str(w) + 'index=', index, ' value=', value, ' i=', i, ' w=', w
                # print e
                print e
                traceback.print_exc()

    def handle_read(self):
        print('received')
        global img_count
        try:
            buf = bytearray(self.recv(image_height * max_width + 1))  # max 29200
            self.receive_buffer.put(buf)
            # print printHex(buf), type(buf)
            print 'buf length', len(buf), ' queue size ', self.receive_buffer.qsize()
            isEnd = buf.find(b'\xff') > -1
            if isEnd:
                print '==================================================', img_count

                if img_count % 5 == 0:
                    check_image = np.zeros((image_height, max_width * 7, 3), dtype=np.uint8)
                    # print check_image.
                    count = 0
                    print 'isempty', self.receive_buffer.empty()
                    while not self.receive_buffer.empty():

                        temp = self.receive_buffer.get()
                        print 'temp length', len(temp)
                        for i in range(len(temp)):
                            self.back_save(check_image, count, temp[i], max_width)
                            count += 1
                            if count == image_height * max_width:
                                break
                        if count == image_height * max_width:
                            break

                    imageio.imwrite(('check_img{0}.png').format(img_count), check_image)
                    # cv2.imshow('check', check_image)
                    print count

                print 'finished ====================================', img_count
                img_count += 1

        except Exception as e:
            print e
            traceback.print_exc()
            pass

        print('end receive')

    def writable(self):
        return (self.buffer.qsize() > 0)

    def handle_write(self):
        print('sent')
        try:
            content = self.buffer.get()
            if content is None:
                print 'nothing to sent'
            print 'sent', len(content)
            # c = printHex(content)
            # print 'c.len', len(c)
            # print 'sent', c

            self.send(bytearray(content))
        except Exception as e:
            print e
            traceback.print_exc()
            pass

    def run_command(self, cmd):
        print "TCP add to buffer"
        self.buffer.put(cmd)


# class HTTPClient(asyncore.dispatcher):
#     def __init__(self, host):
#         asyncore.dispatcher.__init__(self)
#         self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
#         self.connect( (host, 5000) )
#         self.buffer = 'GET %s HTTP/1.0\r\n\r\n' % "a"
#
#     def handle_connect(self):
#         print('connected')
#
#     def handle_close(self):
#         self.close()
#
#     def handle_read(self):
#         print('received')
#         print(self.recv(1024))
#
#     def writable(self):
#         return (len(self.buffer) > 0)
#
#     def handle_write(self):
#         print('sent')
#         sent = self.send(self.buffer.encode('utf-8'))
#         self.buffer = self.buffer[sent:]
#
#     def run_command(self, cmd):
#         self.buffer += cmd
#
#     def handle_error(self):
#         print "error!!!!!!!!!!!!!!!!!!!!!!!!!!!!"

class consume(threading.Thread):
    def __init__(self, data, id, lock, client):
        threading.Thread.__init__(self)
        self.id = id
        self.data = data
        self.lock = lock
        self.client = client
        # self.run(data)

    def run(self):
        while True:
            time.sleep(1)
            self.lock.acquire()
            img = self.data.get()
            print('thread ', self.id, ' consume once,get ', img)

            if not (img is None):
                # print 'send via TCP'
                # add image process function

                # img = np.ones((image_height,max_width), dtype=np.uint8) * 127
                self.client.run_command(bytes(b'\xF0\xF0\xF0'))
                self.client.run_command(img)
                self.client.run_command(bytes(b'\xFF\xFF\xFF'))

            try:
                print('ready to poll')
                asyncore.poll(timeout=.01)
                print('poll!!!!!!!!!!!!!!!!!!!!!!!')
            except Exception as e:
                print e
                pass

            self.lock.release()
            print 'release lock'


class product(threading.Thread):
    def __init__(self, data, id, lock):
        threading.Thread.__init__(self)
        self.id = id
        self.data = data
        self.lock = lock

    def viz(self, y_pred, filename):
        h, w = y_pred.shape
        # -- binary label
        # binary = y_pred.astype(np.uint8)
        # imageio.imwrite('binary_{}.png'.format(filename), binary)
        # -- colored image
        result_rgb = np.zeros((h, w, 3), dtype=np.uint8)
        tp = np.where(y_pred == 1)
        result_rgb[tp[0], tp[1], :] = 0, 255, 0  #
        imageio.imwrite(filename, result_rgb)

    def __trans_save(self, s, p0, p1, w):
        # # print (p1/7 + p0 * w)
        s[p1 / 7 + p0 * w] = s[p1 / 7 + p0 * w] + pow(2, p1 % 7)

    def run_demo(self, args, mirror=False):

        # Define the codec and create VideoWriter object
        height = args.demo_size
        width = int(4.0 / 3 * args.demo_size)

        swidth = int(width / 4)
        sheight = int(height / 4)
        if args.record:
            fourcc = cv2.VideoWriter_fourcc('F', 'M', 'P', '4')
            out = cv2.VideoWriter('output.mp4', fourcc, 20.0, (2 * width, height))

        # print(args.video_source)
        if args.video_source.isdigit():
            args.video_source = int(args.video_source)

        cam = cv2.VideoCapture(args.video_source)
        cam.set(3, width)
        cam.set(4, height)
        key = 0
        idx = 0
        #
        CWD_PATH = os.getcwd()
        resume = os.path.join(CWD_PATH, 'cp_deeplabv3+_20181009-14.45.37_epoch50_lr0.01_50-Finished.pth.tar')
        m_lane = LaneSeg(model='resnet50', param=resume)

        # --- prepare input image
        # from scipy import misc
        font = cv2.FONT_HERSHEY_SIMPLEX
        fps = FPS().start()
        stopflag = False
        count = 0
        while not stopflag:
            # read frame
            idx += 1
            ret_val, img = cam.read()
            if mirror:
                img = cv2.flip(img, 1)

            if img is None:
                break

            cimg = img.copy()
            img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

            # y_pred = m_lane.inference(np.array(img))

            h, w = 480, 640
            # -- binary label
            # binary = y_pred.astype(np.uint8)
            # imageio.imwrite('binary_{}.png'.format(filename), binary)
            # -- colored image
            result_rgb = np.zeros((h, w, 3), dtype=np.uint8)

            step1 = np.zeros((h * max_width), dtype=np.uint8)

            # filename = os.path.join(os.getcwd(), 's.png')
            #
            # step1 = misc.imread(filename)  # input, np array

            self.lock.acquire()
            self.data.add(bytes("hello!"))
            self.lock.release()
            # print('thread ',self.id,' product once, add ',count)
            if IS_DEBUG:
                stopflag = True
                imageio.imwrite('s.png', result_rgb)

            fps.update()

            text2 = '[INFO] approx. FPS: {:.2f}'.format(fps.fps())  # fps.fps()

            # text
            showimg = np.concatenate((result_rgb, cimg), axis=1)
            # 图像     文字内容   坐标                                                                     字体
            cv2.putText(showimg, text2, (0, 20), font,
                        # 大小  颜色 字体厚度
                        0.9, (255, 255, 255), 3)
            cv2.imshow('Demo', showimg)

            key = cv2.waitKey(1)
            if args.record:
                out.write(showimg)
            if key == 27:
                stopflag = True

            # # print(key)
            # space to pause and resume
            if key == 32:
                while True:
                    key = cv2.waitKey(1)
                    if key == 32:
                        break

                    if key == 27:
                        stopflag = True
                        break

                    if key == 115:
                        id = datetime.datetime.now()
                        sourceImgName = "{}_input.png".format(id)
                        resultImgName = "{}_output.png".format(id)
                        imageio.imwrite(sourceImgName, img)
                        imageio.imwrite(resultImgName, result_rgb)

        cam.release()
        if args.record:
            out.release()
        cv2.destroyAllWindows()

    def run(self):
        # getting things ready
        args = Options().parse()
        if args.subcommand is None:
            raise ValueError("ERROR: specify the experiment type")

        # run demo
        self.run_demo(args, mirror=False)


class list_manage():
    def __init__(self):
        self.pool = []

    def get(self):
        if self.pool.__len__() > 0:
            return self.pool.pop()
        else:
            return None

    def add(self, data):
        self.pool.append(data)

    def printf(self):
        print(self.pool)

    def show(self):
        copy = self.pool[:]
        return copy


class LaneSeg():
    def __init__(self, model, param):
        # --- init Network
        # self.net = self.set_network(model=model, param=param)

        # --- to process image
        # self.transforms_rgb = transforms.Compose([
        #    transforms.ToTensor(),
        #   transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
        # ])
        pass

    def set_network(self, model, param):
        # --- creat network
        net = DeepLabv_v3_plus(feature_extractor=model, n_classes=2).cuda()
        net = torch.nn.DataParallel(net)
        # --- load params
        cp_states = torch.load(param)
        net.load_state_dict(cp_states['state_dict'], strict=True)
        net.eval()
        # print('Load Network successed.')
        return net

    def inference(self, image):
        image = self.transforms_rgb(image)  # to tensor, Normalize
        image = torch.unsqueeze(image, 0)  # bs=1
        x = image.float().cuda()
        #  --- inference
        y = self.net(x)
        # --- output
        y = y.cpu().data.numpy()
        y_pred = np.argmax(y[0,], axis=0)  # one-hot: (C, H, W)-->  label: (H, W)
        return y_pred


if __name__ == '__main__':
    # getting things ready
    args = Options().parse()
    # global image_height
    # global image_width
    # Define the codec and create VideoWriter object
    image_height = args.demo_size
    image_width = int(4.0 / 3 * args.demo_size)
    max_width = int(math.ceil(image_width / 7.0))
    img_count = 0
    p = Process(target=boss, args=())
    p.start()
