import http.client, urllib

text = 'American taxpayers have spent more than $100 billion rebuilding Afghanistan, creating schools, hospitals and roads while making millionaires out of a rogues gallery of warlords, gangsters and corrupt officials.A total of $114 billion, which does not include even more spent on the military effort to oust the Taliban and stabilize the impoverished country, has been appropriated since 2002. While it has likely improved conditions in the country, corrupt builders, security providers, mercenaries and even local bankers have all taken their cuts - and gotten rich in the process. Injecting tens of billions of dollars into a small country with insufficient oversight and flawed contracting practices created opportunities for the theft and embezzlement of foreign aid Special Inspector General for Afghanistan Reconstruction (SIGAR) John Sopko told FoxNews.com. Our $114 billion investment in Afghanistan has been plagued by these criminal acts. Special Inspector General for Afghanistan Reconstruction John Sopko has shined a spotlight on billions of U.S. tax dollars wasted in the region since taking the position in 2012.Special Inspector General for Afghanistan Reconstruction John Sopko has shined a spotlight on billions of U.S. tax dollars wasted in the region since taking the position in 2012. (SIGAR HQ) Sopkos office is responsible for rooting out waste, fraud and corruption, and naming those in both Afghanistan and the U.S. who have had their hand in the cookie jar. Virtually every dollar spent has greedy hands grasping at it, according to Sopko.Warlords and militia leaders who established and guard the U.S. supply chain work the system, and demand their percentage. According to a 2010 report entitled "Warlord, Inc.," from the Congressional Committee on Oversight and Government Reform, their methods involve providing “protection” along the supply routes for a fee, turning not-so-veiled threats into cold cash.'
split = text.split(" ")

url = 'https://openwhisk.ng.bluemix.net:443'



data = {'fromLang':'en','toLang':'fr','uuid':'23-fasf-232sdf-2424-sdaf-424-42'}
for word in split:
    data['payload'] = word
    params = urllib.parse.urlencode(data)
    headers = {"Content-type": "application/json",
               "Authorization": "Basic NWEzODIzY2UtN2QwMy00YjNhLWI2MzgtMmNiZDE0NmU1OTliOlN1WFU3VFpWSlM3UEdjTk1iYnVINjF5TmFyd1pMbG5LQkJGNEpDWmFRMGF2bHdHMnZVY3pJcEJvdmJzODkzbGU="}
    conn = http.client.HTTPConnection(url)
    conn.request("POST", "/api/v1/namespaces/UChicago%20CMSC%20330_dev/triggers/LanguageTranslationTrigger", params, headers)

        