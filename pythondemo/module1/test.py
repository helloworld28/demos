bar = 1


def foo():
    global bar
    bar = 2



if __name__ == "__main__":
    foo()
    print bar


def foo2():
    print bar
print bar