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

global counter
counter = 0

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
            console.log(numLines)
            console.log(numChars)
    return {'numChars':numChars, 'numLines':numLines}
