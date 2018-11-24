import re


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
