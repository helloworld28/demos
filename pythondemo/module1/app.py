# coding=utf-8

import eye_gui
from tcp_server import *
from timer_util import *
from web_server import *


def start_app():
    # 开启tcp server
    print ("start tcp server")
    init_cam_data()
    tcp_server = TcpAsyncServer()
    Timer(1, tcp_server.start_server).start()
    gl.gl_tcp_server = tcp_server

    process_cam_data_timer = PerpetualTimer(0.5, process_cam_data)
    process_cam_data_timer.start()
    gl.gl_process_data_timer = process_cam_data_timer

    # 开启web接口
    print ("start web server")
    tornado_thread = ThreadStartWebServer()
    tornado_thread.start()
    gl.gl_web_server = tornado_thread

    # 开启gui
    start_gui()


def start_gui():
    # 开启GUI
    gui = eye_gui.EyeGUI()
    gui.mainloop()


def exit_app():
    gl.gl_system_exit_flag = True
    gl.gl_tcp_server.stop_server()
    gl.gl_web_server.stop()
    gl.gl_process_data_timer.stop()


if __name__ == "__main__":
    start_app()
