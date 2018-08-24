'''
Created on Aug 24, 2018

@author: xiongan2
'''
from numpy import *
from chapter07 import adaboost
import matplotlib.pyplot as plt

datMat, classLabels = adaboost.loadSimpData()
classifierArray, aggClassEst = adaboost.adaBoostTrainDS(datMat, classLabels, 30)
print(len(classifierArray[0]))
print(classifierArray[0])
adaboost.adaClassify([0, 0], classifierArray)
adaboost.adaClassify([[5, 5], [0, 0]], classifierArray)