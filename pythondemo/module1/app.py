import tornado.ioloop
import tornado.web
from process_cam_data import *


class WebHandler(tornado.web.RequestHandler):
    def get(self):
        self.write(str(get_cell_data()))


def make_app():
    return tornado.web.Application([
        (r'/', WebHandler),
    ])


if __name__ == "__main__":
    app = make_app()
    app.listen(8888)

    init_cam_data()
    start_process_data()
    tornado.ioloop.IOLoop.current().start()
