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
    
    fromLang = dict['fromLang']
    toLang = dict['toLang']
    text = dict['text']
    uuid = dict['uuid']
    
    data = {}
    data['username'] = '15ef9931-6ad8-49e0-bb43-60686e91103e'
    data['password'] = 'WJYeqyzPuKQQ'
    data['payload'] = text
    data['context'] = uuid
    data['translateFrom'] = fromLang
    data['translateTo'] = toLang
    
    return data
