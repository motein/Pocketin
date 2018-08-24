'''
Created on Aug 24, 2018

@author: xiongan2
'''
from numpy import *
from chapter07 import adaboost

datMat, classLabels = adaboost.loadSimpData()
classifierArray = adaboost.adaBoostTrainDS(datMat, classLabels, 9)
print(classifierArray)