# coding=utf-8
import re
import select
import threading
from socket import *
import global_var_model as gl


class TcpServer:

    def __init__(self):
        self.running = True

    def start_server(self):
        tcp_server_socket = socket(AF_INET, SOCK_STREAM)
        tcp_server_socket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        addres = ('', gl.gl_tcp_server_port)
        tcp_server_socket.bind(addres)
        tcp_server_socket.listen(50)
        inputs = [tcp_server_socket]

        print("tcp server has startup listen port:{}".format(gl.gl_tcp_server_port))
        while True:
            ready_read, ready_write, exceptional = select.select(inputs, [], [])
            for socket_read in ready_read:
                if socket_read == tcp_server_socket:
                    conn, addr = tcp_server_socket.accept()
                    inputs.append(conn)
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
                        inputs.remove(socket_read)
                        socket_read.close()
            if not self.running:
                break
                self.tcp_server_socket.close()

    def stop_server(self):
        self.running = False


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


class TcpServerThread(threading.Thread):
    def __init__(self, tcp_server):
        threading.Thread.__init__(self)
        self.tcp_server = tcp_server

    def run(self):
        self.tcp_server.start_server()
