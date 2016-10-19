#!/usr/bin/python
import sys

def main(argv):
  line = sys.stdin.readline()
  try:
    while line:
      line = line.rstrip()
      kv = line.split()
      print "" + kv(0) + "\t" + "1"
      line = sys.stdin.readline()
  except "end of file":
    return None


if __name__ == "__main__":
  main(sys.argv)