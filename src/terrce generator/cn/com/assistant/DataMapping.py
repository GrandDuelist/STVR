'''
Created on Aug 30, 2015

@author: zhihan
'''

FILE_SEPERATOR = ": ";
LINE_END_SIGN = "\n"

TOP_DIR = "../../../../../data/TERRACE_STATISTICS"
STATISTIC_FILE_NAME = "statistics"
PATH_SEPERATOR = "/"

OUTPUT_TOP = "../../../../../data/TERRACE_STATISTICS"
OUTPUT_FILE_NAME = "terrace_result.xlsx"


def pasteDir(dir1,dir2):
    return dir1+PATH_SEPERATOR+dir2