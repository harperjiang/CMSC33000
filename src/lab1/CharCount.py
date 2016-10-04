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
    
    fname = dict['name']
    content = dict['content']
    lines = content.split('\n')
    for line in lines:
        numLines += 1
        numChars += len(line)
    return {'_id':fname + "_count", 'numChars':numChars, 'numLines':numLines}
