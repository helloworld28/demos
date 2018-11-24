# coding=utf-8

"""
Image_Algorithm_Toolbox
Visiable tool for labeling bbox.

__author__ = 'JNingWei'
"""

from __future__ import division
import Tkinter as tk
import threading
import time
import tkSimpleDialog
from Tkinter import *
import tkMessageBox
from PIL import Image, ImageTk
import os
import cv2
import global_var_model as gl
import app
import eye_utils

# colors for the bboxes
COLORS = ['cyan', 'blue', 'purple', 'red', 'orange', 'yellow', 'brown', 'pink', 'magenta']

# scaling ratio
SCALING_RATIO = 0

# real_image_size
REAL_IMG_W, REAL_IMG_H = 0, 0


class ShowRealTimeDataThread(threading.Thread):
    """显示实时数据线程"""

    def __init__(self, cam_id, label_tool):
        threading.Thread.__init__(self)
        self.stop_flag = False
        self.cam_id = cam_id
        self.label_tool = label_tool

    def run(self):
        while not self.stop_flag:
            self.label_tool.del_all_real_time_data_box()
            if self.cam_id in gl.gl_cam_data_list:
                cam_data = gl.gl_cam_data_list[self.cam_id]
                for box in cam_data.cam_received_bounding_box:
                    self.label_tool.create_real_time_data_box(box[0:4])

            time.sleep(0.5)
        self.label_tool.del_all_real_time_data_box()


class LabelTool:
    def __init__(self, master):
        # set up the main frame
        self.parent = master
        self.parent.iconbitmap("favicon.ico")
        self.parent.title("吉特天眼系统")
        self.frame = tk.Frame(self.parent)
        self.frame.pack(fill=tk.BOTH, expand=1)
        self.parent.resizable(width=tk.FALSE, height=tk.FALSE)

        # initialize global state
        self.imageDir = ''
        self.imageList = []
        self.egDir = ''
        self.egList = []
        self.outDir = ''
        self.cur = 0
        self.total = 0
        self.category = 0
        self.imagename = ''
        self.labelfilename = ''
        self.tkimg = None

        self.temp_real_time_box_list = list()

        self.temp_read_time_box_fill_list = list()

        self.showRealTimeDataThread = ''

        # initialize mouse state
        self.STATE = {}
        self.STATE['click'] = 0
        self.STATE['x'], self.STATE['y'] = 0, 0

        # reference to bbox
        self.bboxIdList = []
        self.bboxId = None
        self.bboxList = []
        self.bboxDict = dict()
        self.hl = None
        self.vl = None
        self.is_edit_mode = False

        # ----------------- GUI stuff ---------------------

        # dir entry & load
        self.label_top = tk.Label(self.frame, text="欢迎使用吉特天眼系统", font=("Helvetica", 16))
        self.label_top.grid(row=0, column=0, columnspan=2, sticky=tk.E + tk.W)

        self.ldBtn = tk.Button(self.frame, text="Load", command=self.loadDir, border=2)
        self.ldBtn.grid(row=0, column=2, sticky=tk.W + tk.E)
        # main panel for labeling
        self.mainPanel = tk.Canvas(self.frame, cursor='tcross')
        self.mainPanel.bind("<Button-1>", self.leftClick)
        self.mainPanel.bind("<Button-3>", self.rightClick)
        self.mainPanel.bind("<Motion>", self.mouseMove)
        self.parent.bind("<Escape>", self.cancelBBox)  # press <Espace> to cancel current bbox
        self.parent.bind("a", self.prevImage)  # press 'a' to go backforward
        self.parent.bind("d", self.nextImage)  # press 'd' to go forward
        self.mainPanel.grid(row=1, column=1, rowspan=4, sticky=tk.W + tk.N)
        # showing bbox info & delete bbox
        self.lb1 = tk.Label(self.frame, text='Bounding boxes:')
        self.lb1.grid(row=1, column=2, sticky=tk.W)
        self.listbox = tk.Listbox(self.frame, width=30, height=30)
        self.listbox.grid(row=2, column=2, sticky=tk.N)
        self.btnDel = tk.Button(self.frame, text='Delete', command=self.delBBox, border=2)
        self.btnDel.grid(row=3, column=2, sticky=tk.W + tk.E + tk.N)
        self.btnClear = tk.Button(self.frame, text='ClearAll', command=self.clearBBox, border=2)
        self.btnClear.grid(row=4, column=2, sticky=tk.W + tk.E + tk.N)
        self.btnModifyId = tk.Button(self.frame, text='modifyCellId', command=self.show_modify_cell_id_dialog, border=2)
        self.btnModifyId.grid(row=5, column=2, sticky=tk.W + tk.E + tk.N)

        # control panel for image navigation
        self.ctrPanel = tk.Frame(self.frame)
        # self.ctrPanel.pack()
        self.ctrPanel = tk.Frame(self.frame)
        self.ctrPanel.grid(row=6, column=1, columnspan=2, sticky=tk.W + tk.E)
        self.prevBtn = tk.Button(self.ctrPanel, text='<< Prev', width=10, command=self.prevImage, border=5)
        self.prevBtn.pack(side=tk.LEFT, padx=5, pady=3)
        self.nextBtn = tk.Button(self.ctrPanel, text='Next >>', width=10, command=self.nextImage, border=5)
        self.nextBtn.pack(side=tk.LEFT, padx=5, pady=3)
        self.progLabel = tk.Label(self.ctrPanel, text="")
        self.progLabel.pack(side=tk.LEFT, padx=5)
        self.tmpLabel = tk.Label(self.ctrPanel, text="Go to Cam No.")
        self.tmpLabel.pack(side=tk.LEFT, padx=5)
        self.idxEntry = tk.Entry(self.ctrPanel, width=5)
        self.idxEntry.pack(side=tk.LEFT)
        self.goBtn = tk.Button(self.ctrPanel, text='Go', command=self.gotoImage, border=2)
        self.goBtn.pack(side=tk.LEFT)
        self.exitBtn = tk.Button(self.ctrPanel, text='Exit !', width=10, command=self.exit, border=5)
        self.exitBtn.pack(side=tk.RIGHT, padx=5, pady=3)
        self.egPanel = tk.Frame(self.frame, border=10)
        self.egPanel.grid(row=1, column=0, rowspan=5, sticky=tk.N + tk.E)
        self.tmpLabel2 = tk.Label(self.egPanel, text="")
        self.tmpLabel2.pack(side=tk.TOP, pady=5)
        self.egLabels = []
        for i in range(3):
            self.egLabels.append(tk.Label(self.egPanel))
            self.egLabels[-1].pack(side=tk.TOP)
        self.disp = tk.Label(self.ctrPanel, text='')
        self.disp.pack(side=tk.RIGHT)
        self.frame.columnconfigure(1, weight=1)
        self.frame.rowconfigure(4, weight=1)
        self.center_window()
        self.menu()
        self.parent.protocol("WM_DELETE_WINDOW", self.exit)
        threading.Timer(1, self.loadDir).start()

    def scaling_panel(self, image_path):
        screen_w, screen_h = self.parent.winfo_screenwidth(), self.parent.winfo_screenheight()
        h, w, _ = cv2.imread(image_path).shape
        aspect_ratio = w / h
        panel_h = int(screen_h - 250)
        panel_w = int(panel_h * aspect_ratio)
        global SCALING_RATIO
        SCALING_RATIO = panel_h / h
        global REAL_IMG_W, REAL_IMG_H
        REAL_IMG_W, REAL_IMG_H = w, h
        if panel_w > screen_w - 150:
            assert "Sorry, the loaded picture is too wide ! Please resize it first."
        return panel_w, panel_h

    def menu(self):
        menubar = tk.Menu(self.parent)
        display_menu = tk.Menu(menubar, tearoff=0)
        menubar.add_cascade(label='操作', menu=display_menu)
        display_menu.add_command(label="开启实时数据", command=self.enable_show_real_time_data)
        display_menu.add_command(label="关闭实时数据", command=self.unable_show_real_time_data)
        display_menu.add_command(label="进入编辑模式", command=self.set_edit_mode)
        display_menu.add_command(label="退出编辑模式", command=self.out_edit_mode)

        filemenu = tk.Menu(menubar, tearoff=0)
        menubar.add_cascade(label='帮助', menu=filemenu)
        filemenu.add_command(label='<Esc>     cancel')
        filemenu.add_command(label='<A>       prev')
        filemenu.add_command(label='<D>       next')

        self.parent.config(menu=menubar)

    def center_window(self):
        ws, hs = self.parent.winfo_screenwidth(), self.parent.winfo_screenheight()
        w, h = ws - 150, hs - 150
        x = round((ws // 2) - (w // 2))
        y = round((hs // 2) - (h // 2))
        self.parent.geometry('%dx%d+%d+%d' % (w, h, x, y))

    def enable_show_real_time_data(self):
        """开启显示实时数据"""
        if not self.showRealTimeDataThread:
            self.showRealTimeDataThread = ShowRealTimeDataThread(self.imagename, self)
            self.showRealTimeDataThread.start()

    def create_real_time_data_box(self, box):
        box_id = self.create_rectangle_with_box(box)
        if box_id:
            self.temp_real_time_box_list.append(box_id)

    def create_real_time_rect_fill(self, box):
        fill_in_id = self.create_rectangle_fill_in(box)
        if fill_in_id:
            self.temp_real_time_box_list.append(fill_in_id)

    def del_all_real_time_data_box(self):
        while len(self.temp_real_time_box_list) > 0:
            box_id = self.temp_real_time_box_list.pop()
            self.mainPanel.delete(box_id)

    def unable_show_real_time_data(self):
        if self.showRealTimeDataThread:
            self.showRealTimeDataThread.stop_flag = True
            self.del_all_real_time_data_box()
            self.showRealTimeDataThread = None

    def set_edit_mode(self):
        self.is_edit_mode = True

    def out_edit_mode(self):
        self.is_edit_mode = False

    # 加载目录
    def loadDir(self):
        self.category = ''
        self.imageDir = os.path.join('./src', str(self.category))
        self.imageList = [os.path.join(self.imageDir, pic) for pic in os.listdir(self.imageDir)
                          if os.path.splitext(pic)[1] in [".jpg", ".JPG", ".png", ".PNG"]]
        self.imageList = sorted(self.imageList)
        if len(self.imageList) == 0:
            print('No .jpg images found in the specified dir!')
            return
        self.cur = 1
        self.total = len(self.imageList)
        self.outDir = os.path.join('./dst', str(self.category))
        if not os.path.exists(self.outDir):
            os.makedirs(self.outDir)
        self.loadImage()
        print('%d images loaded from %s' % (self.total, self.category))

    def refreshBBox(self):
        for idx in range(len(self.bboxIdList)):
            self.mainPanel.delete(self.bboxIdList[idx])
        self.listbox.delete(0, len(self.bboxList))
        self.bboxIdList = []
        self.bboxList = []

    def loadImage(self):
        image_path = self.imageList[self.cur - 1]
        self.img_1 = Image.open(image_path)
        panel_w, panel_h = self.scaling_panel(image_path)
        self.img = self.img_1.resize((panel_w, panel_h), Image.ANTIALIAS)
        self.tkimg = ImageTk.PhotoImage(self.img)
        self.mainPanel.config(width=self.tkimg.width(), height=self.tkimg.height())
        self.mainPanel.create_image(0, 0, image=self.tkimg, anchor=tk.NW)
        self.progLabel.config(text="%04d/%04d" % (self.cur, self.total))
        self.refreshBBox()
        self.imagename = os.path.split(image_path)[-1].split('.')[0]
        labelname = self.imagename + '.txt'

        self.label_top['text'] = 'cam_id:' + self.imagename

        self.labelfilename = os.path.join(self.outDir, labelname)
        if os.path.exists(self.labelfilename):
            with open(self.labelfilename) as f:
                for (i, line) in enumerate(f):
                    if i == 0:
                        continue
                    tmp_true = [str(t) for t in line.split()]
                    global SCALING_RATIO

                    def scaling(x):
                        return int(x * SCALING_RATIO)

                    tmp_scaled = list(map(int, tmp_true[1:5]))
                    tmp_scaled = list(map(scaling, tmp_scaled))
                    tmp_scaled.insert(0, tmp_true[0])
                    self.bboxList.append(tmp_scaled)
                    self.bboxDict[tmp_true[0]] = tmp_scaled
                    tmpId = self.mainPanel.create_rectangle(tmp_scaled[1], tmp_scaled[2],
                                                            tmp_scaled[3], tmp_scaled[4],
                                                            width=3,
                                                            outline=COLORS[(len(self.bboxList) - 1) % len(COLORS)])
                    self.mainPanel.create_text(tmp_scaled[1] + 15, tmp_scaled[2] + 15, text=str(tmp_true[0]),
                                               fill="red", font="Tine 20 bold")

                    self.bboxIdList.append(tmpId)

                    self.listbox.insert(tk.E, '{:>3s} ({:>3s}, {:>3s}) -> ({:>3s}, {:>3s})'
                                        .format(tmp_true[0], tmp_true[1], tmp_true[2], tmp_true[3], tmp_true[4]))
                    self.listbox.itemconfig(len(self.bboxIdList) - 1,
                                            fg=COLORS[(len(self.bboxIdList) - 1) % len(COLORS)])

        # 实时显示的数据切换
        if self.showRealTimeDataThread:
            self.unable_show_real_time_data()
            self.enable_show_real_time_data()

    def create_rectangle_with_box(self, box):
        global SCALING_RATIO

        def scaling(x):
            return int(x * SCALING_RATIO)

        int_box = list(map(int, box))
        box_scaled = list(map(scaling, int_box))
        try:
            rectangle_id = self.mainPanel.create_rectangle(box_scaled[0], box_scaled[1],
                                                           box_scaled[2], box_scaled[3],
                                                           width=3,
                                                           outline='green')
            return rectangle_id
        except ValueError:
            pass

    def show_modify_cell_id_dialog(self):
        item = self.listbox.curselection()
        if not item:
            tkMessageBox.showwarning('Warn', 'please select one cell first!')
        else:
            dialog = ModifyCellIdDialog(self, self.listbox, self.bboxList[item[0]])

    def create_rectangle_fill_in(self, box):
        """绘带填充的矩形"""
        global SCALING_RATIO

        def scaling(x):
            return int(x * SCALING_RATIO)

        int_box = list(map(int, box))
        box_scaled = list(map(scaling, int_box))
        rectangle_id = self.mainPanel.create_rectangle(box_scaled[0], box_scaled[1],
                                                       box_scaled[2], box_scaled[3],
                                                       fill="red"
                                                       )
        return rectangle_id

    def saveImage(self):
        if self.labelfilename:
            with open(self.labelfilename, 'w') as f:
                f.write('{}\n'.format(len(self.bboxList)))
                self.bboxList.sort()
                global SCALING_RATIO
                recovering = lambda x: int(x / SCALING_RATIO)
                for bbox in self.bboxList:
                    bbox_temp = list(map(recovering, bbox[1:5]))
                    bbox_temp = self.check_border(bbox_temp)
                    line = ' '.join(map(str, bbox_temp))
                    line = str(bbox[0]) + ' ' + line
                    f.write(line + '\n')
            print('Image No. %d saved' % (self.cur))

    def check_border(self, bbox):
        x1, y1, x2, y2 = bbox
        low_check = lambda k, low_limit: k if k > low_limit else low_limit
        high_check = lambda k, high_limit: k if k < high_limit else high_limit
        global REAL_IMG_W, REAL_IMG_H
        x1 = high_check(low_check(x1, 1), REAL_IMG_W - 1)
        y1 = high_check(low_check(y1, 1), REAL_IMG_H - 1)
        x2 = high_check(low_check(x2, 1), REAL_IMG_W - 1)
        y2 = high_check(low_check(y2, 1), REAL_IMG_H - 1)
        return x1, y1, x2, y2

    def leftClick(self, event):
        if self.is_edit_mode:
            if self.STATE['click'] == 0:
                self.STATE['x'], self.STATE['y'] = event.x, event.y
            else:
                x1, x2 = min(self.STATE['x'], event.x), max(self.STATE['x'], event.x)
                y1, y2 = min(self.STATE['y'], event.y), max(self.STATE['y'], event.y)
                box_sequence = self.get_next_box_sequence()
                self.bboxList.append((box_sequence, x1, y1, x2, y2))
                self.bboxDict[box_sequence] = {x1, y1, x2, y2}
                self.bboxIdList.append(self.bboxId)
                self.bboxIdList.sort()
                self.bboxId = None

                recovering = lambda x: int(x / SCALING_RATIO)
                x1, y1, x2, y2 = list(map(recovering, [x1, y1, x2, y2]))
                x1, y1, x2, y2 = self.check_border([x1, y1, x2, y2])

                self.listbox.insert(tk.END, '%s (%d, %d) -> (%d, %d)' % (str(box_sequence), x1, y1, x2, y2))
                self.listbox.itemconfig(len(self.bboxIdList) - 1, fg=COLORS[(len(self.bboxIdList) - 1) % len(COLORS)])

                self.saveImage()
                self.loadImage()
            self.STATE['click'] = 1 - self.STATE['click']

    def get_next_box_sequence(self):

        if self.bboxList:
            num_str = eye_utils.get_last_num(self.bboxList[-1][0])
            num_len = len(num_str)
            num = int(num_str)
            num += 1

            return eye_utils.get_pre_str(self.bboxList[-1][0]) + str(num).zfill(num_len)
        else:
            return 1

    def mouseMove(self, event):
        self.disp.config(text='x: %d, y: %d' % (event.x, event.y))
        if self.is_edit_mode:
            if self.tkimg:
                if self.hl:
                    self.mainPanel.delete(self.hl)
                self.hl = self.mainPanel.create_line(0, event.y, self.tkimg.width(), event.y, width=2)
                if self.vl:
                    self.mainPanel.delete(self.vl)
                self.vl = self.mainPanel.create_line(event.x, 0, event.x, self.tkimg.height(), width=2)
            if 1 == self.STATE['click']:
                if self.bboxId:
                    self.mainPanel.delete(self.bboxId)
                self.bboxId = self.mainPanel.create_rectangle(self.STATE['x'], self.STATE['y'],
                                                              event.x, event.y,
                                                              width=3,
                                                              outline=COLORS[len(self.bboxList) % len(COLORS)])

    def cancelBBox(self, event):
        if 1 == self.STATE['click']:
            if self.bboxId:
                self.mainPanel.delete(self.bboxId)
                self.bboxId = None
                self.STATE['click'] = 0

    def delBBox(self):
        sel = self.listbox.curselection()
        if len(sel) != 1:
            return
        idx = int(sel[0])
        self.mainPanel.delete(self.bboxIdList[idx])
        self.bboxIdList.pop(idx)
        bbox = self.bboxList.pop(idx)
        self.bboxDict.pop(bbox[0])
        self.listbox.delete(idx)
        self.saveImage()
        self.loadImage()

    def clearBBox(self):
        if tkMessageBox.askyesno(title='Warning', message='Are you sure to clear all ?'):
            for idx in range(len(self.bboxIdList)):
                self.mainPanel.delete(self.bboxIdList[idx])
            self.listbox.delete(0, len(self.bboxList))
            self.bboxIdList = []
            self.bboxList = []
            self.bboxDict = dict()

    def rightClick(self, event):
        if 1 == self.STATE['click']:
            self.cancelBBox(event)
        else:
            self.ask_delete(event)

    def ask_delete(self, event):
        if tkMessageBox.askyesno(title='', message='Delete it ?'):
            corresponding_idx = 1000
            for idx, bbox in enumerate(self.bboxList):
                x1, y1, x2, y2 = bbox[0:4]
                if event.x > x1 and event.x < x2 and event.y > y1 and event.y < y2:
                    corresponding_idx = idx
            if corresponding_idx != 1000:
                self.mainPanel.delete(self.bboxIdList[corresponding_idx])
                self.bboxIdList.pop(corresponding_idx)
                self.bboxList.pop(corresponding_idx)
                self.listbox.delete(corresponding_idx)
                self.saveImage()
                self.loadImage()

    def prevImage(self, event=None):
        self.saveImage()
        if self.cur > 1:
            self.cur -= 1
            self.loadImage()

    def nextImage(self, event=None):
        self.saveImage()
        if self.cur < self.total:
            self.cur += 1
            self.loadImage()

    def gotoImage(self):
        idx = int(self.idxEntry.get())
        if 1 <= idx and idx <= self.total:
            self.saveImage()
            self.cur = idx
            self.loadImage()

    def exit(self, event=None):
        self.saveImage()
        self.parent.destroy()
        app.exit_app()

    def modify_cellId(self, bbox, new_value):
        index = self.bboxList.index(bbox)
        self.bboxList.pop(index)
        self.bboxList.insert(index, (new_value, bbox[1], bbox[2], bbox[3], bbox[4]))
        self.saveImage()
        self.loadImage()


class ModifyCellIdDialog(tkSimpleDialog.Dialog):

    def __init__(self, labeling, master, bbox):
        self.bbox = bbox
        self.label = labeling
        tkSimpleDialog.Dialog.__init__(self, master, "修改编号")

    def body(self, master):
        Label(master, text=self.bbox[0]).pack()

        self.e1 = Entry(master)

        self.e1.pack()
        return self.e1  # initial focus

    def apply(self):
        new_cell_value = str(self.e1.get())
        self.label.modify_cellId(self.bbox, new_cell_value)


if __name__ == '__main__':
    root = tk.Tk()
    tool = LabelTool(root)
    root.mainloop()
