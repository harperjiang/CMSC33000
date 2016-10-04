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
import string
import hashlib
import random


def main(dict):
    
    # Time Consumer
    m = hashlib.md5()
    for i in range(1000000):
        m.update(str(random.random()).encode('utf-8'))
        dig = m.digest()
    
    numLines = 0
    numChars = 0
    if 'files' in dict:
        id = dict['_id']
        files = dict['files']
        for file in files:
            fname = file['name']
            content = file['content']
            lines = content.split('\n')
            for line in lines:
                numLines += 1
                numChars += len(line)
        docData = {}
        docData['_id'] = id + "_count"
        docData['numChar'] = numChars
        docData['numLine'] = numLines
        
        return {'doc':docData}
    else:
        return {}

main({})