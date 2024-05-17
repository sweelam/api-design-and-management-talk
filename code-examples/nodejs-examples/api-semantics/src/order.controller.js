const { StatusCodes } =  require('http-status-codes');
const { isApplicationJson, allFieldsAvailable } = require('./utils/http.utils')

const orders = [
  { id: 12, title: 'Black Dock', quantity: 4 },
  { id: 20, title: 'Logitech Mouse', quantity: 1 },
  { id: 17, title: 'Mouse pad', quantity: 10 }
];

module.exports = {

  getOrders: async (req, res) => {
    res.json(orders);
  },

  addOrders: async (req, res) => {
    if (!isApplicationJson(req)) {
      return res.sendStatus(StatusCodes.UNSUPPORTED_MEDIA_TYPE); 
    }

    orders.push(req.body);

    console.log(orders);

    const response = { "msg": "Order added successfully" }
    res.status(StatusCodes.CREATED).json(response);
  },

  updateOrder: async (req, res) => {
    if (!isApplicationJson(req)) {
      return res.sendStatus(StatusCodes.UNSUPPORTED_MEDIA_TYPE); 
    }

    const body = req.body;

    if (!allFieldsAvailable(req.body, orders[0])) {
      console.error("Not all fields available");
      return res.status(StatusCodes.BAD_REQUEST).json({ error: 'Invalid order format' }); 
    }

    console.log("Order before ", orders);

    const index = 
      orders.findIndex(order => order.id === body.id);

    if (index !== -1) {
      orders[index] = body;
      
      console.log("Order after ", orders);
      return res.status(StatusCodes.OK).json({ msg: 'Order updated successfully' }); 
    }else {
      return res.status(StatusCodes.NOT_FOUND).json({ error: 'Order not found' }); 
    }
  }
}