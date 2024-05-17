const orders = [
  { id: 12, title: 'Black Dock', quantity: 4 },
  { id: 20, title: 'Logitech Mouse', quantity: 1 },
  { id: 17, title: 'Mouse pad', quantity: 10 }
];

module.exports = {

  getOrders: async (req, res, next) => {
    res.json({ "msg": "Hello from Express!" });
    next();
  },

  addOrders: async (req, res) => {
    const contentType = req.headers['content-type'];
    if (contentType !== 'application/json') {
      return res.sendStatus(415);     // unsupported media type
    }

    orders.push(req.body);

    console.log(orders)

    const response = { "msg": "Order added successfully" }
    res.status(201).json(response);
  },

  updateOrder: async (req, res) => {
    const contentType = req.headers['content-type'];
    if (contentType !== 'application/json') {
      return res.sendStatus(415); 
    }

    const body = req.body;

    if (!body.id || !body.title || !body.quantity) {
      return res.status(400).json({ error: 'Invalid order format' }); 
    }

    console.log("Order before ", orders);

    const index = orders.findIndex(order => order.id === body.id);

    if (index !== -1) {
      orders[index] = body;
      console.log("Order after ", orders);
      return res.status(200).json({ msg: 'Order updated successfully' }); 
    }else {
      return res.status(404).json({ error: 'Order not found' }); 
    }
  }
}