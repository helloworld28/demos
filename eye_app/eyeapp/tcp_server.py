# coding=utf-8
import asyncore
import re
import select
from socket import *

import global_var_model as gl


class SocketClientHandler(asyncore.dispatcher_with_send):
    def handle_read(self):
        data = self.recv(8192)
        if data:
            # 1.校验完整性
            pattern = re.compile("^\{(.+)\}$")
            if pattern.match(data):
                put_data(data)
                print ("receive data[{}]".format(data))
            else:
                print("invalid data[{}]".format(data))


class TcpAsyncServer(asyncore.dispatcher):
    def __init__(self):
        asyncore.dispatcher.__init__(self)
        self.create_socket(AF_INET, SOCK_STREAM)
        self.set_reuse_addr()
        self.bind(('', gl.gl_tcp_server_port))
        self.listen(50)

    def handle_accept(self):
        pair = self.accept()
        if pair is not None:
            sock, address = pair
            print("{} connected ".format(str(address)))
            SocketClientHandler(sock)

    def start_server(self):
        asyncore.loop()

    def stop_server(self):
        asyncore.close_all()


class TcpServer:

    def __init__(self):
        self.running = True
        self.tcp_server_socket = socket(AF_INET, SOCK_STREAM)
        self.tcp_server_socket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        self.address = ('', gl.gl_tcp_server_port)
        self.tcp_server_socket.bind(self.address)
        self.tcp_server_socket.listen(50)
        self.inputs = [self.tcp_server_socket]
        print("tcp server has startup listen port:{}".format(gl.gl_tcp_server_port))

    def receive_data(self):
        while self.running and not gl.gl_system_exit_flag:
            ready_read, ready_write, exceptional = select.select(self.inputs, [], [], 1)
            for socket_read in ready_read:
                if socket_read == self.tcp_server_socket:
                    conn, addr = self.tcp_server_socket.accept()
                    self.inputs.append(conn)
                    print("{} connected ".format(str(addr)))
                else:
                    data = socket_read.recv(4096)
                    if data:
                        # 1.校验完整性
                        pattern = re.compile("^\{(.+)\}$")
                        if pattern.match(data):
                            put_data(data)
                            print ("receive data[{}]".format(data))
                        else:
                            print("invalid data[{}]".format(data))
                    else:
                        self.inputs.remove(socket_read)
                        socket_read.close()
        for socket_input in self.inputs:
            try:
                socket_input.close()
            except Exception:
                print "close socket error"

    def stop_server(self):
        self.running = False

    def start_receive_data(self):
        self.receive_data()


def put_data(data):
    regex = r'\[(.+?)\]'
    cam_id = ""
    cam_bounding_box_list = list()
    matches = re.finditer(regex, data, re.MULTILINE)
    for matchNum, match in enumerate(matches):
        if matchNum == 0:
            cam_id = str(match.group(1))
        else:
            bounding_box = match.group(1).split(',')
            if len(bounding_box) == 5:
                cam_bounding_box_list.append(bounding_box)
                gl.gl_received_cam_data[cam_id] = cam_bounding_box_list
