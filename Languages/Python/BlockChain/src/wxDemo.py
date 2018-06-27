import wx

def OnClose(event):
    dlg = wx.MessageDialog(top, "Do you really want to close this application?","Confirm Exit", wx.OK|wx.CANCEL|wx.ICON_QUESTION)
    result = dlg.ShowModal()
    dlg.Destroy()
    if result == wx.ID_OK:
        top.Destroy()

app = wx.App(redirect=True)
top = wx.Frame(None, title="Hello World", size=(300,200))
top.Bind(wx.EVT_CLOSE, OnClose)
top.Show()
app.MainLoop()