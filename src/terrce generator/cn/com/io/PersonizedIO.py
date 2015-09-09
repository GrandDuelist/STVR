'''
Created on Aug 29, 2015

@author: zhihan
'''
from cn.com.assistant import DataMapping





def tansFileToList(fileName):
    result = {}
    f = open(fileName)
    line = f.readline()   
    while line:
       # print(line)
        key = getKeyFromFileLine(line)
        value = getValueFromFileLine(line)
        result[key] = value
        line = f.readline()
    f.close()
    return(result)
 
'''
line sample: fileNum: 1
'''
    
def getKeyFromFileLine(line):
    position = line.find(DataMapping.FILE_SEPERATOR)
    return line[:position]

def getValueFromFileLine(line):
    position = line.find(DataMapping.FILE_SEPERATOR)+len(DataMapping.FILE_SEPERATOR)
    return line[position:line.find(DataMapping.LINE_END_SIGN)]