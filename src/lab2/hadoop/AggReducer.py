#!/usr/bin/python
import sys

def main(argv):
  currentkey = ''
  count = 0
  line = sys.stdin.readline()
  try:
    while line:
      line = line.rstrip()
      key = line.split()(0)
      
      if key == currentkey:
          count += 1
      else:
          print(currentkey + '\t' + count)
                
          currentkey = key
          count = 1
      line = sys.stdin.readline()
  except "end of file":
    return None

if __name__ == "__main__":
  main(sys.argv)