from threading import Timer

import global_var_model as gl


class PerpetualTimer:
    """loop timer"""

    def __init__(self, interval, hFunction):
        self.interval = interval
        self.hFunction = hFunction
        self.thread = Timer(self.interval, self.handle_function)

    def handle_function(self):
        if not gl.gl_system_exit_flag:
            self.hFunction()
            self.thread = Timer(self.interval, self.handle_function)
            self.thread.start()

    def start(self):
        self.thread.start()

    def stop(self):
        self.thread.cancel()
