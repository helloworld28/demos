# coding=utf-8

import os

import cal_occupy as occupy
from cam_data import *


def init_cam_data():
    """从配置文件初始化摄像头的信息及各个格子状态"""
    cam_list = os.listdir(os.path.join(gl.gl_bounding_setting_dir))
    for cam_id in cam_list:
        cam_id = cam_id.replace(".txt", "")
        cam_data = CamData(cam_id)
        bounding_setting = get_cam_bounding_setting(cam_id)
        cam_data.cam_cell_bounding_box_setting = bounding_setting
        cam_data.init_cell_status()
        gl.gl_cam_data_list[cam_id] = cam_data


def process_data(cam_id, bounding_box_list):
    """
    计算占用格子情况
    1.先获取这个cam对应的格子坐标配置
    2.初始化这个摄像头的所有格子状态为-1
    3.循环bounding box 计算其中心点在哪个格子里面
    在哪个格子里面就设置它的状态
    4.针对两种类型（货架类型，空卡板类型）同时出现在一个格子里时，只考虑货物
    5.最后把没有标记格子的状态改为空（0），表示没有货物及空卡板

    :param cam_id: 摄像头的ID
    :param bounding_box_list: 识别到的物体bounding box 如：[476,229,537,330,person]
    :return:
    """
    # 1.
    cam_bounding_settings = get_cam_bounding_setting(cam_id)
    # 2.
    reset_cam_cell_status(cam_id)
    # 3.
    for (j, bounding_box) in enumerate(bounding_box_list):
        box = occupy.cal_in_which_box(cam_bounding_settings, bounding_box)
        if box:
            cell_id = box[0]
            if bounding_box[4] == 'person':
                update_cell_status(cam_id, cell_id, 2)
                # 4.
            if bounding_box[4] == 'holder' and not is_exists_stock_in_cell(cam_id, cell_id):
                update_cell_status(cam_id, cell_id, 1)
    # 5.
    for setting_bounding_box in cam_bounding_settings:
        if is_nothing_in_cell(cam_id, setting_bounding_box[0]):
            update_cell_status(cam_id, setting_bounding_box[0], 0)

    gl.gl_cam_data_list[cam_id].cam_received_bounding_box = bounding_box_list


def update_cell_status(cam_id, cell_id, status):
    if cam_id not in gl.gl_cam_data_list:
        gl.gl_cam_data_list[cam_id] = CamData(cam_id)
    cam_data = gl.gl_cam_data_list[cam_id]
    cam_data.update_cell_status(cell_id, status)
    cam_data.cam_status = 1
    cam_data.update_time = time.time()


def reset_cam_cell_status(cam_id):
    if cam_id not in gl.gl_cam_data_list:
        gl.gl_cam_data_list[cam_id] = CamData(cam_id)
    cam_data = gl.gl_cam_data_list[cam_id]
    cam_data.reset_cell_status()


def is_exists_stock_in_cell(cam_id, cell_id):
    """判断格子是否已经有货架"""
    if cam_id in gl.gl_cam_data_list:
        if cell_id in gl.gl_cam_data_list[cam_id].cam_cell_status:
            return gl.gl_cam_data_list[cam_id].cam_cell_status[cell_id] == 2
    return False


def is_nothing_in_cell(cam_id, cell_id):
    """判断格子里是否是没有物体"""
    if cam_id in gl.gl_cam_data_list:
        if cell_id in gl.gl_cam_data_list[cam_id].cam_cell_status:
            return gl.gl_cam_data_list[cam_id].cam_cell_status[cell_id] == -1
    return True


def get_cell_data():
    """merge all cam cell data"""
    result = dict()
    for (cam_id, cam_data) in sorted(gl.gl_cam_data_list.items()):
        result.update(cam_data.cam_cell_status)
    return result


def get_cam_bounding_setting(cam_id):
    bounding_file_name = os.path.join(gl.gl_bounding_setting_dir, cam_id + '.txt')
    result_array = []
    if os.path.exists(bounding_file_name):
        with open(bounding_file_name) as f:
            for (i, line) in enumerate(f):
                if i == 0:
                    continue
                result_array.append([str(t) for t in line.split()])

    else:
        print ("can not find the cam_id bounding setting!!!")
    return result_array


def process_cam_data():
    for cam_id in gl.gl_cam_data_list:
        if cam_id in gl.gl_received_cam_data:
            bounding_box_list = gl.gl_received_cam_data.pop(cam_id)
            if bounding_box_list:
                process_data(cam_id, bounding_box_list)
