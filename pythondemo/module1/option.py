import argparse
import os

class Options():
	def __init__(self):
		self.parser = argparse.ArgumentParser(description="parser for Lane Segmentation")
		subparsers = self.parser.add_subparsers(title="subcommands", dest="subcommand")

		# demo
		demo_arg = subparsers.add_parser("demo", help="parser for Lane Segmentation arguments")

		demo_arg.add_argument("--record", type=int, default=0,
								help="set it to 1 for recording into video file")
		demo_arg.add_argument("--demo-size", type=int, default=480,
								help="demo window height, default 480")
		demo_arg.add_argument("--video-source", type=str, required=True,
								help="source of video import, if camera using 0 which is the number of camera, if video, give filename")

	def parse(self):
		return self.parser.parse_args()
