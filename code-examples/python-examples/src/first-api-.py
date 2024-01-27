from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/api')
def api():
    return jsonify({'data': 'Hello, API Design and Management!'})

if __name__ == '__main__':
    app.run(debug=True)
