'''
Created on Aug 29, 2015

@author: zhihan
'''
from cn.com.controller import VersionController, MainController

#tst = VersionController.VersionController("tst1","tst2");
MainController.main()

# 
# from openpyxl import Workbook
# wb = Workbook()
# 
# # grab the active worksheet
# ws = wb.active
# 
# # Data can be assigned directly to cells
# ws['A1'] = 42
# 
# # Rows can also be appended
# ws.append([1, 2, 3])
# 
# # Python types will automatically be converted
# import datetime
# ws['A2'] = datetime.datetime.now()
# 
# # Save the file
# wb.save("sample.xlsx")