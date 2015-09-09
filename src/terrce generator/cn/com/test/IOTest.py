'''
Created on Aug 30, 2015

@author: zhihan
'''

from cn.com.io import PersonizedIO
from cn.com.assistant import DataMapping
result = PersonizedIO.tansFileToList("/home/zhihan/Workspace/STVR/data/TERRACE_STATISTICS/Mobile Firefox/22/statistics")
#print(PersonizedIO.getValueFromFileLine("file: 1"))
#print(DataMapping.FILE_SEPERATOR)

#result = PersonizedIO.tansFileToList(""")
print(result)