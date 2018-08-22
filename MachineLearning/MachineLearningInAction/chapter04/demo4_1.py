'''
Created on Aug 22, 2018

@author: xiongan2
'''
from chapter04 import bayes

listPosts, listClasses = bayes.loadDataSet()
myVocabList = bayes.createVocabList(listPosts)
print(myVocabList)

vec = bayes.setOfWords2Vec(myVocabList, listPosts[0])
print(vec)

vec = bayes.setOfWords2Vec(myVocabList, listPosts[3])
print(vec)