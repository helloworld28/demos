#!/usr/bin/env python
# -*- coding: UTF-8 -*-
import Tkinter as tk

from Tkinter import *


class WidgetDemo:
    def __init__(self):
        window = Tk()
        window.title("组建demo")

        frame1 = Frame(window)
        frame1.pack(fill=Y,expand=1)

        self.v1 = IntVar()
        cbtBold = Checkbutton(frame1, text="粗体", variable=self.v1, command=self.processCheckbutton)
        self.v2 = IntVar()
        rbRed = Radiobutton(frame1, text="红色", bg="red", variable=self.v2, value=1, command=self.processRaidobutton)
        rbYellow = Radiobutton(frame1, text="黄色", bg="yellow", variable=self.v2, value=2,
                               command=self.processRaidobutton)
        cbtBold.grid(row=1, column=1)
        rbRed.grid(row=1, column=2)
        rbYellow.grid(row=1, column=3)

        frame2 = Frame(window)
        frame2.pack(fill=X)

        label = Label(frame2, text="请输入名字")
        self.name = StringVar()
        entryName = Entry(frame2, textvariable=self.name)
        btGetName = Button(frame2, text="获取名字", command=self.processButton)
        message = Message(frame2, text="组建demo")


        label.grid(row=1, column=1)
        entryName.grid(row=1, column=2)
        btGetName.grid(row=1, column=3)
        message.grid(row=1, column=4)

        text = Text(window)
        text.pack(fill=BOTH)
        text.insert(END, "Hello")
        text.insert(END, "wolrd")

        window.mainloop()

    def processCheckbutton(self):
        print("复选框按钮是"+("被选中" if self.v1.get() == 1 else "未选中"))

    def processRaidobutton(self):
        print ("红色是"+("被选中" if self.v2.get() == 1 else "未选中"))
    def processButton(self):
        print ("你的名字："+self.name.get())

WidgetDemo()
