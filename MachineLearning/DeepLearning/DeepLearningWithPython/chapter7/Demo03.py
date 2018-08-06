'''
Created on Aug 6, 2018

@author: xiongan2
'''
import numpy as np
from keras.models import Sequential
from keras.layers import Dense, Activation
from keras.utils.np_utils import to_categorical
from keras.utils.vis_utils import plot_model

model = Sequential()
model.add(Dense(32, input_dim=500))
model.add(Activation(activation='relu'))
model.add(Dense(10))
model.add(Activation(activation='softmax'))
model.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['categorical_accuracy'])

data = np.random.random((1000, 500))
labels = to_categorical(np.random.randint(10, size=(1000, 1)))

score = model.evaluate(data,labels, verbose=0)
print("Before Training:", zip(model.metrics_names, score))

model.fit(data, labels, epochs=10, batch_size=32, verbose=0)

score = model.evaluate(data,labels, verbose=0)
print("After Training:", zip(model.metrics_names, score))

plot_model(model, to_file='s3.png', show_shapes=True)