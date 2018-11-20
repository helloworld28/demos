# coding=utf-8
import time
import global_var_model as gl


class CamData:
    """cam data include the cells status"""

    def __init__(self, cam_id):
        self.cam_id = cam_id
        # 0 init 1 online 2 offline
        self.cam_status = 0
        # 各个格子目前的状态-1，表示未知 0为空，1 为空卡板 ，2 为有货
        self.cam_cell_status = dict()
        self.cam_cell_bounding_box_setting = list()
        self.update_time = time.time()
        self.cam_received_bounding_box = list()

        print ("new instance of cam[{}]".format(self.cam_id))

    def get_cam_cell_status(self):
        if self.is_outdated():
            self.set_out_dated()
        return self.cam_cell_status

    def set_out_dated(self):
        """数据过期 把格子状态改为未知"""
        print("the cam[{}] data is out date".format(self.cam_id))
        self.cam_status = 2
        for key in self.cam_cell_status:
            self.cam_cell_status[key] = -1
        self.cam_received_bounding_box = list()

    def is_outdated(self):
        return (time.time() - self.update_time) > gl.gl_cam_data_expired_time

    def update_cell_status(self, cell_id, status):
        # print("update_cell_status {},{}, {}".format(self.cam_id, cell_id, status))
        self.cam_cell_status[cell_id] = status

    def reset_cell_status(self):
        for cell_id in self.cam_cell_status:
            self.update_cell_status(cell_id, -1)

    def init_cell_status(self):
        if self.cam_cell_bounding_box_setting:
            for cell_id, item in enumerate(self.cam_cell_bounding_box_setting):
                self.update_cell_status(cell_id, -1)
