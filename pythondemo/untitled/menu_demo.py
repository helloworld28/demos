# coding=utf-8
from Tkinter import *
class MenuDemo:
    def __init__(self):
        window = Tk()
        window.title("Menu demo")

        menubar = Menu(window, bg="reg")
        window.config(menu = menubar)

        operateMenu = Menu(menubar, tearoff =0)
        menubar.add_cascade(labl)