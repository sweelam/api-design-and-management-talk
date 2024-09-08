from flask import Flask, jsonify
from flask_httpauth import HTTPBasicAuth
from werkzeug.security import generate_password_hash, check_password_hash


app = Flask(__name__)
auth = HTTPBasicAuth()

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

@auth.verify_password
def verify_password(username, password):
    if username == users[0]['first_name'] and check_password_hash(generate_password_hash("kyc"), password):
        return username


@app.get('/users')
@auth.login_required
def get_all_users():
    return jsonify(users), 200

if __name__ == '__main__':
    app.run(port=6625, debug=True)
