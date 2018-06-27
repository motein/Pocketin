from flask import Flask, request, jsonify, render_template

app=Flask(__name__)

@app.route('/')
@app.route('/<user>')
def index(user=None):
    #return 'This is the homepage'
    return render_template("user.html", user=user)

@app.route('/tuna')
def tuna():
    return '<h1>Tunna is good</h1>'

@app.route('/pofile/<username>')
def profile(username):
    return "<h2>Hey there %s</h2>" % username

@app.route('/post/<int:post_id>')
def show_post(post_id):
    return "<h2>Post ID is %s</h2>" % post_id

@app.route('/showmethod')
def show_method():
    return "Medthod used: %s" % request.method

@app.route('/showmethod2', methods=['GET', 'POST'])
def show_method2():
    if request.method=='POST':
        return "You are using POST"
    else:
        return "You are using GET"
    
@app.route('/pofile2/<username>')
def profile2(username):
    return render_template("profile.html", name=username)

@app.route('/shopping')
def shopping():
    food={"Cheese", "Tuna", "Beef", "Toothpaste"}
    return render_template("shopping.html", food=food)

@app.route('/getjson', methods=['GET'])
def getJson():
    response={
        'message':'Our chain was replaced',
        'new_chain':2,
        }
        
    return jsonify(response),200

if __name__=="__main__":
    app.run(port=5001,debug=True)