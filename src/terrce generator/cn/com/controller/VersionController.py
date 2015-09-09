'''
Created on Aug 30, 2015

@author: zhihan
'''

import os
from cn.com.assistant import DataMapping
import openpyxl


from cn.com.io import PersonizedIO
class VersionController:
    def __init__(self,productName,versionName,ws):
        self.versionName = versionName
        self.productName = productName
        self.ws = ws
        self.versionDir = DataMapping.TOP_DIR+DataMapping.PATH_SEPERATOR+self.productName+DataMapping.PATH_SEPERATOR+self.versionName
        self.readConstant()
        self.outputVesionConstant()
        self.outputClusters()
       
        
    def outputVesionConstant(self):
        
        return 0
        
    
    def readConstant(self):
        self.constants = PersonizedIO.tansFileToList(self.versionDir+DataMapping.PATH_SEPERATOR+DataMapping.STATISTIC_FILE_NAME)
        
        
    
    def outputClusters(self):
        print("outptuClusters")
        
        
    