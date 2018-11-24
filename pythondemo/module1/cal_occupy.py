#!/usr/bin/env python
# -*- coding: UTF-8 -*-


def get_set(from_x, from_y, to_x, to_y):
    result_set = set()
    from_x = int(from_x)
    from_y = int(from_y)
    to_x = int(to_x)
    to_y = int(to_y)
    for i in range(from_x, to_x):
        for j in range(from_y, to_y):
            result_set.add("{}_{}".format(i, j))
    return result_set


def cal_occupy(range1_from_x, range1_from_y, range1_to_x, range1_to_y,
               range2_from_x, range2_from_y, range2_tox, range2_to_y):
    set1 = get_set(range1_from_x, range1_from_y, range1_to_x, range1_to_y)
    set2 = get_set(range2_from_x, range2_from_y, range2_tox, range2_to_y)
    set3 = set1.difference(set2)
    occupy_percent = 100 - (len(set3) * 100 / len(set1))
    return occupy_percent


def cal_in_which_box(bounding_box_settings, bounding_box):
    """计算bounding box 落中哪个集合中"""
    middle_x, middle_y = cal_middle_x_y(bounding_box)
    for box in bounding_box_settings:
        if int(box[1]) <= middle_x <= int(box[3]) and int(box[2]) <= middle_y <= int(box[4]):
            return box


def cal_middle_x_y(bounding_box):
    return round((int(bounding_box[0]) + int(bounding_box[2])) / 2), round(
        (int(bounding_box[1]) + int(bounding_box[3])) / 2)


def is_include(range1_from_x, range1_from_y, range1_to_x, range1_to_y,
               range2_from_x, range2_from_y, range2_to_x, range2_to_y, tolerance):
    return range1_from_x <= (range2_from_x + tolerance) and range1_to_x >= (
            range2_to_x - tolerance) and range1_from_y <= (range2_from_y + tolerance) and range1_to_y >= (
                   range2_to_y - tolerance)
