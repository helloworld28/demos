# -*- coding: utf-8 -*-
# @Time    : 2018/11/21  10:26
# @Author  : liuyun
# @File    : process_bar2.py
import threading
import time

import requests
import os
from datetime import datetime

download_list = list()


class DownloadBar():

    def __init__(self, url, filename):

        self.url = url
        self.filename = filename

    def down(self):
        self.resp = requests.get(self.url, stream=True)
        self.total_size = int(self.resp.headers["content-Length"])
        down_t = threading.Thread(target=self.download_file, args=[])

        down_t.start()
        self.download_progress()

    def download_file(self):
        # url = "https://qd.myapp.com/myapp/qqteam/pcqq/QQ9.0.7_1.exe"
        self.start = datetime.now()
        with open(self.filename, 'wb') as f:
            for chunk in self.resp.iter_content(512):
                if chunk:
                    f.write(chunk)

    def download_progress(self):
        # 已下载/花费总时间
        self.file_size = 0
        print("开始下载:总大小:%s Mb" % (self.total_size / 1024 / 1024))
        while self.file_size < self.total_size:
            self.file_size = os.path.getsize(self.filename)
            now = datetime.now()
            cost_time = (now - self.start).seconds
            if cost_time != 0:
                self.rate = self.file_size / 1024 / 1024 / cost_time
            else:
                self.rate = 0
            if self.rate != 0:
                self.remain_time = int((self.total_size - self.file_size) / (self.rate * 1024 * 1024))
            else:
                self.remain_time = ".."
            self.view_bar()
            time.sleep(1)

    def view_bar(self):
        num = int(50 * (self.file_size / float(self.total_size)))
        blank = 50 - num
        self.rate = "%.2f" % float(self.rate)

        bar = " [%s%s]%.2f%%  速度:%s Mb/s 剩余: %ss " % (
        "#" * num, " " * blank, num * 100 / 50, self.rate, self.remain_time)
        return bar


def print_bar():
    while download_list:
        bar_str = ''
        for download in download_list:
            bar_str += download.view_bar()
           # bar_str += '\n'
        print ('\r %s' % bar_str),
        time.sleep(1)


downbar = DownloadBar("https://qd.myapp.com/myapp/qqteam/pcqq/QQ9.0.7_1.exe", "QQ9.0.7_1.exe")
threading.Thread(target=downbar.down).start()
download_list.append(downbar)
downbar2 = DownloadBar("https://qd.myapp.com/myapp/qqteam/pcqq/QQ9.0.7_1.exe", "QQ9.0.7_2.exe")
download_list.append(downbar2)
threading.Thread(target=downbar2.down).start()
downbar3 = DownloadBar("https://qd.myapp.com/myapp/qqteam/pcqq/QQ9.0.7_1.exe", "QQ9.0.7_3.exe")
download_list.append(downbar3)
threading.Thread(target=downbar3.down).start()

threading.Timer(10, print_bar).start()
