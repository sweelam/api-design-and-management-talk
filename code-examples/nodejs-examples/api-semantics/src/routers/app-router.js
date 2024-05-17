const express = require('express');
const router = express.Router();
const index = require('../order.controller');

router.get('/api/orders', index.getOrders);
router.post('/api/orders', index.addOrders);
router.put('/api/orders', index.updateOrder);

module.exports = router