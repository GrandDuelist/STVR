'''
Created on Sep 7, 2015

@author: zhihan
'''
from Main import T2XInternal, DataMapping
import os
from Main.T2XInternal import charAdd

fileName = os.path.join(DataMapping.INPUT_TOP_DIR,"16.txt")
#print charAdd('A',0)
T2XInternal.tranOneProduct(DataMapping.INPUT_TOP_DIR,DataMapping.outputFileName)
#T2XInternal.tranOneFile(fileName,'A',"lda_greedy")
# contents = T2XInternal.readFromFile(fileName)
# 
# T2XInternal.writeToExcel(DataMapping.outputFileName,"No targets", 'A',contents,"string")