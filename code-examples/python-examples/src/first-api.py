from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/api-design/orders/<order_id>')
def api(order_id):
    return jsonify({"orderId":order_id,"orderValue":99.90,"productId":1,"quantity":1})

if __name__ == '__main__':
    app.run(debug=True)
