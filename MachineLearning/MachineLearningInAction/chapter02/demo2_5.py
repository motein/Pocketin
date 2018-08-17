'''
Created on Aug 13, 2018

@author: xiongan2
'''
from os import listdir
import numpy as np
from chapter02 import kNN

def handwritingClassTest():
    hwLabels = []
    trainingFileList = listdir('trainingDigits')           #load the training set
    m = len(trainingFileList) # number of files
    trainingMat = np.zeros((m, 1024)) # m files, 1024 elements per file
    
    for i in range(m): # initialize
        fileNameStr = trainingFileList[i]
        fileStr = fileNameStr.split('.')[0]     #take off .txt
        classNumStr = int(fileStr.split('_')[0])
        hwLabels.append(classNumStr)
        trainingMat[i,:] = kNN.img2vector('trainingDigits/%s' % fileNameStr) 
        
    testFileList = listdir('testDigits')        #iterate through the test set
    errorCount = 0.0
    mTest = len(testFileList)
    for i in range(mTest):
        fileNameStr = testFileList[i]
        fileStr = fileNameStr.split('.')[0]     #take off .txt
        classNumStr = int(fileStr.split('_')[0])
        vectorUnderTest = kNN.img2vector('testDigits/%s' % fileNameStr)
        classifierResult = kNN.classify(vectorUnderTest, trainingMat, hwLabels, 3)
        print("the classifier came back with: %d, the real answer is: %d" % (classifierResult, classNumStr))
        if (classifierResult != classNumStr):
            errorCount += 1.0
            
    print("\nthe total number of errors is: %d" % errorCount)
    print("\nthe total error rate is: %f" % (errorCount/float(mTest)))
    
if __name__ == "__main__":
    handwritingClassTest()