# coding=utf-8
import tornado.ioloop
import tornado.web
from process_cam_data import *
from labeling import *


class WebHandler(tornado.web.RequestHandler):
    def get(self):
        self.write(str(get_cell_data()))


class ThreadStartWebServer(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)

    def run(self):
        app = make_app()
        app.listen(8888)
        tornado.ioloop.IOLoop.current().start()


def make_app():
    return tornado.web.Application([
        (r'/', WebHandler),
    ])


if __name__ == "__main__":
    print ("start tcp server")
    # 开启tcp server
    init_cam_data()
    start_process_data()

    # 开启web接口
    print ("start web server")
    ThreadStartWebServer().start()

    # 开启GUI
    root = tk.Tk()

    tool = LabelTool(root)
    root.mainloop()
