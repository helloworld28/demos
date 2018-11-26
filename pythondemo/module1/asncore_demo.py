import asyncore, socket
import threading


class HTTPClient(asyncore.dispatcher):

    def __init__(self, host, path):
        asyncore.dispatcher.__init__(self)
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connect((host, 7788))
        self.buffer = 'GET %s HTTP/1.0\r\n\r\n' % path

    def handle_connect(self):
        print "handle_connect!!!!!!!!!!!!!!!!!"
        pass

    def handle_close(self):
        print "handle_close!!!!!!!!!!!!!!!!!"
        self.close()
        timer = threading.Timer(1, retry_connect)
        timer.start()
        timer.join()

    def handle_read(self):
        print self.recv(8192)

    def writable(self):
        return (len(self.buffer) > 0)

    def handle_write(self):
        sent = self.send(self.buffer)
        self.buffer = self.buffer[sent:]


def retry_connect():
    client = HTTPClient('192.168.1.251', '/')


client = HTTPClient('192.168.1.251', '/')
asyncore.loop()
