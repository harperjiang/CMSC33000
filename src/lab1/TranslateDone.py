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
    payload = dict['payload']
    uuid = dict['context']
    
    url = "http://www.example.com/callback?uuid={}&data={}".format(uuid,payload)
    
    return {'payload':url}
