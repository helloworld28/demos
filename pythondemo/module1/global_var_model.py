# coding=utf-8

# 各个cam数据列表
gl_cam_data_list = dict()

gl_bounding_setting_dir = "./dst"

# 判断存在货物的比例
gl_exits_stock_percent = 40
# 判断空卡板的比例
gl_empty_card_percent = 50

gl_tcp_server_port = 7788

gl_received_cam_data = dict()

# 数据过期时间（秒）
gl_cam_data_expired_time = 30

# 退出系统标识，用于判断终止一些while true 循环
gl_system_exit_flag = False

gl_tcp_server = None
gl_web_server = None
gl_process_data_timer = None
