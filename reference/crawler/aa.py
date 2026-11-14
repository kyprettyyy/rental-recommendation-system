import requests
import pandas as pd
import re
from lxml import etree
import time
import datetime

for i in [1,2,3,4,5,6,7]:
    curr_time = datetime.datetime.now()
    timestamp = datetime.datetime.strftime(curr_time, '%Y-%m-%d_%H_%M_%S')

    with open('input/data'+timestamp+'.txt', 'w', encoding='utf-8') as f:
        url = 'https://bj.lianjia.com/zufang/rco11/pg' + str(i) + '/#contentList'
        resp = requests.get(url)
        time.sleep(0.5)
        resp_text = resp.text
        tree = etree.HTML(resp.text)
        info_list = tree.xpath('//div[@class="content__list"]/div')
        for info in info_list:
            name = info.xpath('./a/@title')
            # detail_1 = info.xpath('.//div[@class="content__list--item--main"]/p[2]//text()')
            location = info.xpath('.//div[@class="content__list--item--main"]/p[2]/a/text()')
            detail_list = info.xpath('.//div[@class="content__list--item--main"]/p[2]/text()')
            detail_string = ''.join(detail_list)
            detail_text = re.sub('[\r\n\t\s//\-]', '', detail_string)
            price = tree.xpath('//*[@id="content"]/div[1]/div[1]/div[1]/div/span//text()')
            price = ''.join(price)
            # detail_1 = info.xpath('.//div[@class="content__list--item--main"]/p/a/text()')
            # detail_2 = info.xpath('.//div[@class="content__list--item--main"]/p/i/text()')
            info = str(name) + "\t" + str(location) + "\t" + str(detail_text) + "\t" + str(price)
            print(info)
            # f.write("{},{},{},{}\n".format(name,location,detail_text,price))
            f.write(info+'\n')
            f.flush()
        f.close()

