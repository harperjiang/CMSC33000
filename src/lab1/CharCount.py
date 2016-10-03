#
#
# main() will be invoked when you Run This Action.
# 
# @param Whisk actions accept a single parameter,
#        which must be a JSON object.
#
# @return which must be a JSON object.
#         It will be the output of this action.
#
#
import sys

def main(dict):
    numLines = 0
    numChars = 0
    if 'files' in dict:
        files = dict['files']
        for f in files:
            fname = f['name']
            content = f['content']
            lines = content.split('\n')
            for line in lines:
                numLines += 1
                numChars += len(line)
            print(numLines)
            print(numChars)
    return {'numChars':numChars, 'numLines':numLines}
