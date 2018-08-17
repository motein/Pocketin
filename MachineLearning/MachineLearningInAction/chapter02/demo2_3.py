'''
Created on Aug 13, 2018

@author: xiongan2
'''
import numpy as np
from chapter02 import kNN

def classifyPerson():
    resultList = ['not at all', 'in small doses', 'in large doses']
    percentTats = float(input("percentage of time spent playing video games?"))
    ffMiles = float(input("frequent flier miles earned per year?"))
    iceCream = float(input("liters of ice cream consumed per year?"))
    datingDataMat, datingLabels = kNN.file2matrix("datingTestSet2.txt")
    normMat, ranges, minVal = kNN.autoNorm(datingDataMat)
    inArr = np.array([ffMiles, percentTats, iceCream])
    classifyResult = int(kNN.classify((inArr-minVal)/ranges, normMat, datingLabels,3))
    print("You will probaly like this person: ", resultList[classifyResult-1])

if __name__ == "__main__":
    classifyPerson()