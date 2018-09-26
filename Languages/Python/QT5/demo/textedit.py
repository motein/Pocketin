'''
Created on Sep 26, 2018

@author: xiongan2
'''
import sys, os
import numpy as np
from PySide2.QtWidgets import QMainWindow, QApplication, QFileDialog,\
    QTableWidget, QTableWidgetItem, QAction, qApp, QWidget, QSlider, QLabel,\
    QCheckBox, QPushButton, QVBoxLayout, QRadioButton, QTextEdit
from PySide2.QtCore import Qt
from pip._vendor.pyparsing import delimitedList
import csv
from pandas.core.internals import BoolBlock

class Notepad(QWidget):
    def __init__(self):
        super().__init__()
        self.text = QTextEdit(self)
        self.clr_btn = QPushButton('Clear')
        
        self.init_ui()
        
    def init_ui(self):
        
        layout = QVBoxLayout()
        layout.addWidget(self.text)
        layout.addWidget(self.clr_btn)
#         self.clr_btn.clicked.connect(self.clear_text)
        self.clr_btn.clicked.connect(self.save_text)
        
        self.setLayout(layout)
        self.show()
    def clear_text(self):
        self.text.clear()
        
    def save_text(self):
        with open('test.txt', 'w') as f:
            my_text = self.text.toPlainText()
            f.write(my_text)
        
app = QApplication(sys.argv)
a_wind = Notepad()
sys.exit(app.exec_())