'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter05 import logRegress
from pprint import pprint
import numpy as np

dataArr, labelMat = logRegress.loadDataSet()
# print(dataArr + labelMat)
weights = logRegress.stocGradAscent0(np.array(dataArr), labelMat)
print(weights)
logRegress.plotBestFit(weights)

weights = logRegress.stocGradAscent1(np.array(dataArr), labelMat)
print(weights)
logRegress.plotBestFit(weights)
# 
# weights = logRegress.stocGradAscent1(np.array(dataArr), labelMat, 500)
# print(weights)
# logRegress.plotBestFit(weights)