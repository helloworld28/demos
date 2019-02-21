import re
from threading import Timer
import global_var_model as gl

def get_last_num(text):
    regex = r"(\d+)$"
    matches = re.search(regex, str(text))
    if matches:
        return matches.group(1)
    else:
        return '0'


def get_pre_str(text):
    regex = r"(\d+)$"
    matches = re.search(regex, str(text))
    if matches:
        start_index = matches.start(1);
        return text[0:start_index]
    else:
        return ''

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
