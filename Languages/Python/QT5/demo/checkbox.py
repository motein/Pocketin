'''
Created on Sep 26, 2018

@author: xiongan2
'''
import sys, os
import numpy as np
from PySide2.QtWidgets import QMainWindow, QApplication, QFileDialog,\
    QTableWidget, QTableWidgetItem, QAction, qApp, QWidget, QSlider, QLabel,\
    QCheckBox, QPushButton, QVBoxLayout
from PySide2.QtCore import Qt
from pip._vendor.pyparsing import delimitedList
import csv
from pandas.core.internals import BoolBlock

class Window(QWidget):
    def __init__(self):
        super().__init__()
        self.init_ui()
        
    def init_ui(self):
        self.lbl = QLabel()
        self.chx = QCheckBox('Do you like dogs?')
        self.btn = QPushButton('Push me!')
        
        layout = QVBoxLayout()
        layout.addWidget(self.lbl)
        layout.addWidget(self.chx)
        layout.addWidget(self.btn)
        
        self.setLayout(layout)
        
        self.btn.clicked.connect(lambda:self.btn_click(self.chx.isChecked(), self.lbl))
        
        self.show()
        
    def btn_click(self, chk, lbl):
        if chk:
            lbl.setText('I guess you like dogs')
        else:
            lbl.setText("Dog hater then")
    
        
        
app = QApplication(sys.argv)
a_wind = Window()
sys.exit(app.exec_())