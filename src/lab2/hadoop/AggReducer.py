#!/usr/bin/python
import sys

def main(argv):
  currentkey = ''
  count = 0
  line = sys.stdin.readline()
  try:
    while line:
      line = line.rstrip()
      key = line.split()[0]
      
      if key == currentkey:
          count += 1
      else:
          if currentkey != '':
              print(currentkey + '\t' + str(count))
                
          currentkey = key
          count = 1
      line = sys.stdin.readline()
    
    print(currentkey + '\t' + str(count))
  except "end of file":
    print(currentkey + '\t' + str(count))

if __name__ == "__main__":
  main(sys.argv)