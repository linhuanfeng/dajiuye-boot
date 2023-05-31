# 发送请求并返回请求内容
import requests  # 用于发送URL请求
import pandas as pd # 用于构造数据框
import random  # 用于产生随机数
import time  # 用于时间停留# 
 
# 根据第一页的URL，抓取“数据分析师”岗位的信息
url = r'https://fe-api.zhaopin.com/c/i/sou?pageSize=60&cityId=489&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=%E6%95%B0%E6%8D%AE%E5%88%86%E6%9E%90%E5%B8%88&kt=3&lastUrlQuery=%7B%22jl%22:%22489%22,%22kw%22:%22%E6%95%B0%E6%8D%AE%E5%88%86%E6%9E%90%E5%B8%88%22,%22kt%22:%223%22%7D&at=9c5682b1a4f54de89c899fb7efc7e359&rt=54eaf1be1b8845c089439d53365ea5dd&_v=0.84300214&x-zp-page-request-id=280f6d80d733447fbebafab7b8158873-1541403039080-617179'
# 构造请求的头信息，防止反爬虫
headers = {    'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'}
# 利用requests包中的get函数发送请求
response = requests.get(url, headers = headers)
# 基于response返回Json数据
datas = response.json()
datas
# 基于字典的索引方法，取出所需的字段
# 根据Json数据返回每一条招聘信息# 返回公司名称
company = [i['company']['name'] for i in response.json()['data']['results']]
# 返回公司规模
size = [i['company']['size']['name'] for i in response.json()['data']['results']]
# 返回公司类型
type = [i['company']['type']['name'] for i in response.json()['data']['results']]
# 返回公司招聘信息
positionURL = [i['positionURL'] for i in response.json()['data']['results']]
# 返回工作经验的要求
workingExp = [i['workingExp']['name'] for i in response.json()['data']['results']]
# 返回教育水平的要求
eduLevel = [i['eduLevel']['name'] for i in response.json()['data']['results']]
# 返回薪资水平
salary = [i['salary'] for i in response.json()['data']['results']]
# 返回工作岗位名称
jobName = [i['jobName'] for i in response.json()['data']['results']]
# 返回福利信息
welfare = [i['welfare'] for i in response.json()['data']['results']]
# 返回岗位所在城市
city = [i['city']['items'][0]['name'] for i in response.json()['data']['results']]
# 返回经度
lat = [i['geo']['lat'] for i in response.json()['data']['results']]
# 返回纬度
lon = [i['geo']['lon'] for i in response.json()['data']['results']]
# 将返回的信息构造表格
pd.DataFrame({'company':company,'size':size,'type':type,'positionURL':positionURL,
              'workingExp':workingExp,'eduLevel':eduLevel,'salary':salary,
              'jobName':jobName,'welfare':welfare,'city':city,'lat':lat,'lon':lon})