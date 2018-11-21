import threading
from threading import Timer

from process_cam_data import *
from tornado import web, ioloop
class WebHandler(web.RequestHandler):
    def get(self):
        self.write(str(get_cell_data()))


class ThreadStartWebServer(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.tornado_instance = None

    def run(self):
        app = make_app()
        app.listen(8888)
        self.tornado_instance = ioloop.IOLoop.current()
        self.tornado_instance.start()

    def stop_web(self):
        self.tornado_instance.stop()

    def stop(self):
        self.tornado_instance.stop()
        if self.isAlive():
            self.join()


def make_app():
    return web.Application([
        (r'/', WebHandler),
    ])