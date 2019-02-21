import websocket
import json

import requests

try:
    import thread
except ImportError:
    import _thread as thread
import time

clientId = "";
request_handler = dict();


def on_message(ws, message):
    msg = json.loads(message);
    print(msg)
    if msg["type"] == "org.mockserver.serialization.model.WebSocketClientIdDTO":
        global clientId
        clientId = json.loads(msg["value"], encoding="utf-8")["clientId"]
        print("the clientId is ", clientId)
        content = {"httpRequest": {"method": "GET", "path": "/that/"},

                   "times": {"remainingTimes": 1, "unlimited": False},
                   "httpResponseObjectCallback": {"clientId": clientId}}
        requests.put("http://127.0.0.1:5003/expectation", json=content)

    if msg["type"] == "org.mockserver.model.HttpRequest":
        print ("AAAAAAAAAAAAAAAAAAAAAAAAAAAA")

        # respond = json.dumps(
        #     {"type": "org.mockserver.model.HttpResponse", "value": {"statusCode": "200", "body": "i'm a teapot"}})
        respond = json.dumps({"statusCode": "200", "body": "i'm a teapot"})
        print(respond)
        ws.send(respond)


def on_error(ws, error):
    print(error)


def on_close(ws):
    print("### closed ###")


def on_open(ws):
    def run(*args):
        pass
        print("thread terminating...")


if __name__ == "__main__":
    websocket.enableTrace(True)
    host = "127.0.0.1"
    port = "5003"
    ws = websocket.WebSocketApp("ws://" + host + ":" + port + "/_mockserver_callback_websocket",
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()
