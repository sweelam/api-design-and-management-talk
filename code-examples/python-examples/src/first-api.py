from flask import Flask, jsonify, request
import json

app = Flask(__name__)

class User:
    def __init__(self, id, first_name, last_name, age) -> None:
        self.id = id
        self.first_name = first_name
        self.last_name = last_name
        self.age = age

users = [
    User(1,"Mohamed", "Ahmed", 41).__dict__,
    User(2,"Alaa", "Mansour", 33).__dict__,
    User(3,"Alex", "Nilson", 38).__dict__,
    User(4,"Bahaa", "Turk", 55).__dict__,
    User(5,"Nilson", "Mandella", 90).__dict__
]


@app.get('/users')
def get_all_users():
    return jsonify(users), 200

@app.get('/users/<id>')
def get_user_by_id(id):
    for user in users:
        if user['id'] == int(id):
            return jsonify(user), 200
        
    return jsonify({}), 404

@app.delete('/users/<int:id>')
def delete_user_by_id(id):
    for user in users:
        if user['id'] == id:
            print ("users count before remove ", len(users))
            users.remove(user)
            print ("users count after remove ", len(users))
            return jsonify(user) , 200
    
    return jsonify({}), 204

@app.post('/users')
def add_new_user():
    new_user = request.get_json()
    print ("users count before add ", len(users))
    users.append(new_user)
    print ("users count after add ", len(users))
    return jsonify(new_user) , 201

if __name__ == '__main__':
    app.run(port=6625, debug=True)
