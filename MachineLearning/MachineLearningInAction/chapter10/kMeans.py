'''
Created on Aug 17, 2018

@author: xiongan2
'''
 # -*- coding: utf-8 -*-
 
from numpy import *
import json
import urllib
import requests
from time import sleep
import matplotlib
import matplotlib.pyplot as plt
from urllib.request import urlopen, quote
import hashlib

def loadDataSet(fileName):
    dataMat = []
    fr = open(fileName)
    for line in fr.readlines():
        curLine = line.strip().split('\t')
        fltLine = list(map(float, curLine))
        dataMat.append(fltLine)
    return dataMat

def distEclud(vecA, vecB):
    return sqrt(sum(power(vecA - vecB, 2)))

def randCent(dataSet, k):
    n = shape(dataSet)[1]
    centroids = mat(zeros((k, n)))
    for j in range(n):
        minJ = min(dataSet[:, j])
        rangeJ = float(max(dataSet[:, j]) - minJ) # max minus min in one column
        centroids[:,j] = mat(minJ + rangeJ * random.rand(k,1))
    return centroids # k �� n

def kMeans(dataSet, k, distMeas=distEclud, createCent=randCent): # 80 �� 2
    m = shape(dataSet)[0] # 80
    clusterAssment = mat(zeros((m, 2))) # 80 �� 2
    
    centroids = createCent(dataSet, k) # k �� n(columns)
    clusterChanged = True
    while clusterChanged:
        clusterChanged =  False
        for i in range(m):
            minDist = inf;
            minIndex = -1
            for j in range(k):
                distJI = distMeas(centroids[j,:],dataSet[i,:]) # distance between jth centroid and ith point
                if distJI < minDist:
                    minDist = distJI
                    minIndex = j
            if clusterAssment[i, 0] != minIndex: # which cluster
                clusterChanged = True
            clusterAssment[i, :] = minIndex, minDist**2 # cluster, distance's square
#         print(centroids)
        for cent in range(k): #recalculate centroids
            ptsInClust = dataSet[nonzero(clusterAssment[:, 0].A==cent)[0]] #get all the point in this cluster
            centroids[cent, :] = mean(ptsInClust, axis = 0)
            
    return centroids, clusterAssment

def biKmeans(dataSet, k, distMeas=distEclud): # 80 �� 2
    m = shape(dataSet)[0]
    clusterAssment = mat(zeros((m,2))) # 80 �� 2
    centroid0 = mean(dataSet, axis=0).tolist()[0] # X, Y centroid
    centList =[centroid0] # create a list with one centroid (x, y) style
    
    for j in range(m): # calc initial Error
        clusterAssment[j, 1] = distMeas(mat(centroid0), dataSet[j,:])**2 # a^2 + b^2 (2nd column is the distance's square)
    
    while (len(centList) < k): # start from 1
        lowestSSE = inf
        for i in range(len(centList)):
            ptsInCurrCluster = dataSet[nonzero(clusterAssment[:,0].A==i)[0],:]# get the data points currently in cluster i
            centroidMat, splitClustAss = kMeans(ptsInCurrCluster, 2, distMeas)
            sseSplit = sum(splitClustAss[:,1]) #compare the SSE to the current minimum
            sseNotSplit = sum(clusterAssment[nonzero(clusterAssment[:,0].A!=i)[0],1])
#             print("sseSplit, and notSplit: ",sseSplit,sseNotSplit)
            if (sseSplit + sseNotSplit) < lowestSSE:
                bestCentToSplit = i
                bestNewCents = centroidMat
                bestClustAss = splitClustAss.copy()
                lowestSSE = sseSplit + sseNotSplit
                
        bestClustAss[nonzero(bestClustAss[:,0].A == 1)[0],0] = len(centList) #change 1 to 3,4, or whatever
        bestClustAss[nonzero(bestClustAss[:,0].A == 0)[0],0] = bestCentToSplit
#         print('the bestCentToSplit is: ',bestCentToSplit)
#         print('the len of bestClustAss is: ', len(bestClustAss))
        centList[bestCentToSplit] = bestNewCents[0,:].tolist()[0]#replace a centroid with two best centroids 
        centList.append(bestNewCents[1,:].tolist()[0])
        clusterAssment[nonzero(clusterAssment[:,0].A == bestCentToSplit)[0],:]= bestClustAss#reassign new clusters, and SSE
        
    return mat(centList), clusterAssment

def geoGrab(stAddress, city):
    apiStem = 'https://maps.googleapis.com/maps/api/geocode/json?'  #create a dict and constants for the goecoder
    params = {}
    params['address'] = '%s %s' % (stAddress, city)
    url_params = urllib.parse.urlencode(params)
    googleApi = apiStem + url_params      #print url_params
    print(googleApi)
    response = requests.get(googleApi)
    resp_json = response.json()
#     return resp_json['results'][0]['geometry']['location']
    return resp_json

def geoGrabFromBaidu(stAddress):
    apiStem = 'https://api.map.baidu.com/geocoder/v2/?'  #create a dict and constants for the goecoder
    ak = 'Ws3VbrelSruFeyTiprO5QGCBTsF4wAX0'
    baiduApi = apiStem + "address=%s&output=json&ak=%s" % (stAddress, ak)
    print(baiduApi)
    response = requests.get(baiduApi)
    return response.json()

def geoGrabFromBaidu2(stAddress):
    header = 'https://api.map.baidu.com'
    ak = 'Ws3VbrelSruFeyTiprO5QGCBTsF4wAX0'
    sk = 'Cr9ypbtIG2G7aN4D5pGCF1Ow9N7T1fyc'
    
    queryStr = '/geocoder/v2/?address=%s&output=json&ak=%s' % (stAddress, ak)
    encodedStr = urllib.parse.quote(queryStr, safe="/:=&?#+!$,;'@()*[]")
    
    rawStr = encodedStr + sk
    encodedStr = urllib.parse.quote_plus(rawStr)
    encodedStr = encodedStr.encode(encoding='utf_8')
    sn = hashlib.md5(encodedStr).hexdigest()
    
    baiduApi = header + queryStr + "&sn=%s" % (sn)
    print(baiduApi)
    response = requests.get(baiduApi)
    return response.json()

def massPlaceFind(fileName):
    fw = open('places.txt', 'w')
    for line in open(fileName).readlines():
        line = line.strip()
        lineArr = line.split('\t')
        geoResults = geoGrab(lineArr[1], lineArr[2])
        if geoResults['status'] == 'OK':
            retDict = geoResults['results'][0]['geometry']['location']
            lat = float(retDict['lat'])
            lng = float(retDict['lng'])
            print("%s\t%f\t%f" % (lineArr[0], lat, lng))
            fw.write('%s\t%f\t%f\n' % (line, lat, lng))
        else:
            print("error fetching")
        sleep(1)
    fw.close()
    
def distSLC(vecA, vecB): # Spherical Law of Cosines
    a = sin(vecA[0,1]*pi/180) * sin(vecB[0,1]*pi/180)
    b = cos(vecA[0,1]*pi/180) * cos(vecB[0,1]*pi/180) * cos(pi * (vecB[0,0]-vecA[0,0]) /180)
    return arccos(a + b)*6371.0 # pi is imported with numpy

def clusterClubs(numClust=5):
    datList = []
    for line in open('places.txt').readlines():
        lineArr = line.split('\t')
        datList.append([float(lineArr[4]), float(lineArr[3])])
    datMat = mat(datList)
    myCentroids, clustAssing = biKmeans(datMat, numClust, distMeas=distSLC)
    fig = plt.figure()
    rect=[0.1,0.1,0.8,0.8]
    scatterMarkers=['s', 'o', '^', '8', 'p', \
                    'd', 'v', 'h', '>', '<']
    axprops = dict(xticks=[], yticks=[])
    ax0=fig.add_axes(rect, label='ax0', **axprops)
    imgP = plt.imread('Portland.png')
    ax0.imshow(imgP)
    ax1=fig.add_axes(rect, label='ax1', frameon=False)
    for i in range(numClust):
        ptsInCurrCluster = datMat[nonzero(clustAssing[:,0].A==i)[0],:]
        markerStyle = scatterMarkers[i % len(scatterMarkers)]
        ax1.scatter(ptsInCurrCluster[:,0].flatten().A[0], ptsInCurrCluster[:,1].flatten().A[0], marker=markerStyle, s=90)
    ax1.scatter(myCentroids[:,0].flatten().A[0], myCentroids[:,1].flatten().A[0], marker='+', s=300)
    plt.show()
    