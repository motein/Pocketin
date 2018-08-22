'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter04 import bayes
import numpy as np

listPosts, listClasses = bayes.loadDataSet()
# print(len(listClasses))
myVocabList = bayes.createVocabList(listPosts)
# print(myVocabList)
# print("different words: %s \n" % len(myVocabList))

trainMat = []
for postinDoc in listPosts:
#     print(postinDoc)
    trainMat.append(bayes.setOfWords2Vec(myVocabList, postinDoc))
    
# print(np.shape(trainMat))
# print(trainMat)
# print(len(trainMat))

# p0Num = np.ones(32)
# print(p0Num / 2.0)

p0V,p1V, pAb = bayes.trainNB0(trainMat, listClasses)

print(pAb)
print(p0V)
print(p1V)