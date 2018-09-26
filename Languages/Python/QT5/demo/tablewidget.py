'''
Created on Sep 26, 2018

@author: xiongan2
'''
import sys, os
from PySide2 import QtCore, QtGui, QtWidgets
import numpy as np
from PySide2.QtWidgets import QMainWindow, QApplication, QFileDialog,\
    QTableWidget, QTableWidgetItem, QAction, qApp
from pip._vendor.pyparsing import delimitedList
import csv

class MyTable(QtWidgets.QTableWidget):
    def __init__(self, r, c):
        super().__init__(r, c)
        self.check_changed = True
        self.init_ui()
    
    def init_ui(self):
        self.cellChanged.connect(self.c_current)
        self.show()
        
    def c_current(self):
        if self.check_changed:
            row = self.currentRow()
            col = self.currentColumn()
            value = self.item(row, col)
            value = value.text()
            print(row, col)
            print(value)

    def open_sheet(self):
        self.check_changed = False
        path = QFileDialog.getOpenFileName(self, 'Open CSV', os.getenv("HOME"), 'CSV(*.csv)')
        if path[0] != '':
            with open(path[0], newline='') as csv_file:
                self.setRowCount(0)
                self.setColumnCount(10)
                my_file = csv.reader(csv_file, delimiter=',', quotechar='|')
                for row_data in my_file:
                    row = self.rowCount()
                    self.insertRow(row)
                    if len(row_data) > 10:
                        self.setColumnCount(len(row_data))
                    for column, stuff in enumerate(row_data):
                        item = QTableWidgetItem(stuff)
                        self.setItem(row, column, item)
        self.check_changed = True
        
    def save_sheet(self):
        path = QFileDialog.getOpenFileName(self, 'Save CSV', os.getenv("HOME"), 'CSV(*.csv)')
        if path[0] != '': 
            with open(path[0], 'w') as csv_file:
                writer = csv.writer(csv_file, dialect='excel')
                for row in range(self.rowCount()):
                    row_data = []
                    for column in range(self.colorCount()):
                        item = self.item(row, column)
                        if item  is not None:
                            row_data.append(item.text())
                        else:
                            row_data.append('')
                    print(row_data)
                    writer.writerow(row_data)
        
class Sheet(QMainWindow):
    def __init__(self):
        super().__init__()
        
        self.form_widget = MyTable(4, 4)
        self.setCentralWidget(self.form_widget)
        col_headers = ['A', 'B', 'C', 'D']
        self.form_widget.setHorizontalHeaderLabels(col_headers)
        
        number = QtWidgets.QTableWidgetItem('4')
        self.form_widget.setCurrentCell(1, 1)
        self.form_widget.setItem(1, 1, number)
        
        # Set up menu
        bar = self.menuBar()
        file = bar.addMenu('File')
        
        save_action = QAction('&Save', self)
        save_action.setShortcut("Ctrl+S")
        
        open_action = QAction('&Open', self)
        
        quit_action = QAction('&Quit', self)
        
        file.addAction(save_action)
        file.addAction(open_action)
        file.addAction(quit_action)
        
#         quit_action.triggered.connect(self.quit_app)
        save_action.triggered.connect(self.form_widget.save_sheet)
        open_action.triggered.connect(self.form_widget.open_sheet)
        
        
        self.show()
        
    def quit_app(self):
        qApp.quit()
        
app = QApplication(sys.argv)
sheet = Sheet()
sys.exit(app.exec_())