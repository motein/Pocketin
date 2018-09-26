'''
Created on Sep 26, 2018

@author: xiongan2
'''
'''
Created on Sep 26, 2018

@author: xiongan2
'''
import sys, os
from PySide2 import QtCore, QtGui, QtWidgets
import numpy as np
from PySide2.QtWidgets import QMainWindow, QApplication, QFileDialog,\
    QTableWidget, QTableWidgetItem, QAction, qApp, QWidget, QSlider
from PySide2.QtCore import Qt
from pip._vendor.pyparsing import delimitedList
import csv

class Window(QtWidgets.QWidget):
    def __init__(self):
        super().__init__()
        self.init_ui()
        
    def init_ui(self):
        self.le = QtWidgets.QLineEdit()
        self.b1 = QtWidgets.QPushButton('Clear')
        self.b2 = QtWidgets.QPushButton('Print')
        self.sl = QtWidgets.QSlider(Qt.Horizontal)
        self.sl.setMinimum(1)
        self.sl.setMaximum(99)
        self.sl.setValue(25)
        self.sl.setTickInterval(10)
        self.sl.setTickPosition(QSlider.TicksAbove)
        
        
        v_box = QtWidgets.QVBoxLayout()
        v_box.addWidget(self.le)
        v_box.addWidget(self.b1)
        v_box.addWidget(self.b2)
        v_box.addWidget(self.sl)
        
        self.setLayout(v_box)
        self.setWindowTitle('PyQt5 LineEdit')
        
#         self.b1.clicked.connect(self.btn_clk)
#         self.b2.clicked.connect(self.btn_clk)
        self.b1.clicked.connect(lambda:self.btn_clk2(self.b1, 'Hello from Clear'))
        self.b2.clicked.connect(lambda:self.btn_clk2(self.b2, 'Hello from Print'))
        self.sl.valueChanged.connect(self.v_change)
        
        self.show()
        
    def btn_clk(self):
        sender = self.sender()
        if sender.text() == 'Print':
            print(self.le.text())
        else:
            self.le.clear()
            
    def btn_clk2(self, b, string):
        if b.text() == 'Print':
            print(self.le.text())
        else:
            self.le.clear()
        
        print(string)
            
    def v_change(self):
        my_value = str(self.sl.value())
        self.le.setText(my_value)
        
app = QtWidgets.QApplication(sys.argv)
a_wind = Window()
sys.exit(app.exec_())